<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.article"); // 自定义一个命名空间
article = Ext.Authority.article; // 定义命名空间的别名
article = {
	all : ctx + '/article/all',// 加载所有
	save : ctx + "/article/save",//保存
	del : ctx + "/article/del/",//删除
	crawl : ctx + "/article/crawl",
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})'),
	pagesizes:eval('(${fields.pagesizes==null?"{}":fields.pagesizes})'),
	pages:eval('(${fields.pages==null?"{}":fields.pages})'),
	pageSize : 20 // 每页显示的记录数
};
/** 改变页的combo */
article.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					article.pageSize = parseInt(comboBox.getValue());
					article.bbar.pageSize = parseInt(comboBox.getValue());
					article.store.baseParams.limit = article.pageSize;
					article.store.baseParams.start = 0;
					article.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
article.pageSize = parseInt(article.pageSizeCombo.getValue());
/** 基本信息-数据源 */
article.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : article.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : article.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'webId', 'webUrl', 'title', 'content', 'subscriber', 'subscribeDate', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					article.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
article.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					article.deleteAction.enable();
					article.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					article.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
article.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 240
			},
			columns : [article.selModel, {
						hidden : true,
						header : '字段ID',
						dataIndex : 'id'
					}, {
						header : '站点',
						dataIndex : 'webId',
						renderer : function(v) {
							return Share.map(v,article.pages , '');
						}
					}, {
						header : '标题',
						dataIndex : 'title'
					}, {
						header : '内容',
						dataIndex : 'content'
					}, {
						header : '地址',
						dataIndex : 'webUrl'						
					}, {
						header : '来源',
						dataIndex : 'subscriber'						
					}, {
						header : '发布日期',
						dataIndex : 'subscribeDate'						
					}, {
						header : '创建日期',
						dataIndex : 'createDate'
					}]
		});
/** 新建 */
article.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'field_add',
			handler : function() {
				article.addWindow.setIconClass('field_add'); // 设置窗口的样式
				article.addWindow.setTitle('新建文章'); // 设置窗口的名称
				article.addWindow.show().center(); // 显示窗口
				article.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				article.enabledCombo.clearValue();
			}
		});
/** 编辑 */
article.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'field_edit',
			disabled : true,
			handler : function() {
				var record = article.grid.getSelectionModel().getSelected();
				article.addWindow.setIconClass('field_edit'); // 设置窗口的样式
				article.addWindow.setTitle('编辑文章'); // 设置窗口的名称
				article.addWindow.show().center();
				article.formPanel.getForm().reset();
				article.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
article.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'field_delete',
			disabled : true,
			handler : function() {
				article.delFun();
			}
		});
/** 查询 */
article.searchField = new Ext.ux.form.SearchField({
			store : article.store,
			paramName : 'webUrl',
			emptyText : '请输入标题名称',
			style : 'margin-left: 5px;'
		});
		
article.crawlAction = new Ext.Action({
	text : '执行Crawl',
	iconCls : 'synchro',
	handler : function() {
		Share.AjaxRequest({
					url : article.crawl,
					callback : function(json) {
						
					}
				});
	}
});

/** 顶部工具栏 */
article.tbar = [article.addAction, '-', article.editAction, '-',
		article.deleteAction, '-', article.searchField, '-', article.crawlAction];
/** 底部工具条 */
article.bbar = new Ext.PagingToolbar({
			pageSize : article.pageSize,
			store : article.store,
			displayInfo : true,
			items : ['-', '&nbsp;', article.pageSizeCombo]
		});
/** 基本信息-表格 */
article.grid = new Ext.grid.GridPanel({
			store : article.store,
			colModel : article.colModel,
			selModel : article.selModel,
			tbar : article.tbar,
			bbar : article.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
		

article.pageCombo = new Ext.form.ComboBox({
	fieldLabel : '站点',
	hiddenName : 'webId',
	name : 'webId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(article.pages)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

/** 基本信息-详细信息的form */
article.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '字段信息',
			bodyStyle : 'padding:10px;border:0px',
			labelwidth : 50,
			defaultType : 'textfield',
			items : [{
						xtype : 'hidden',
						fieldLabel : 'ID',
						name : 'id',
						anchor : '99%'
					}, article.pageCombo,
					{
						fieldLabel : '标题',
						maxLength : 256,
						allowBlank : false,
						name : 'title',
						anchor : '99%'
					}, {
						xtype : 'textarea',
						height : 120,
						fieldLabel : '内容',
						maxLength : 4096,
						allowBlank : false,
						name : 'content',
						anchor : '99%'
					}, {
						fieldLabel : '来源',
						maxLength : 256,
						allowBlank : false,
						name : 'subscriber',
						anchor : '99%'
					}, {
						fieldLabel : '地址',
						maxLength : 128,
						allowBlank : false,
						name : 'webUrl',
						anchor : '99%'
					}
					]
		});
/** 编辑新建窗口 */
article.addWindow = new Ext.Window({
			layout : 'fit',
			width : 420,
			height : 360,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [article.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							article.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = article.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
article.alwaysFun = function() {
	Share.resetGrid(article.grid);
	article.deleteAction.disable();
	article.editAction.disable();
};
article.saveFun = function() {
	var form = article.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : article.save,
				params : form.getValues(),
				callback : function(json) {
					article.addWindow.hide();
					article.alwaysFun();
					article.store.reload();
				}
			});
};
article.delFun = function() {
	var record = article.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '确定要删除这条记录吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : article.del + record.data.id,
								callback : function(json) {
									article.alwaysFun();
									article.store.reload();
								}
							});
				}
			});
};
article.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [article.grid]
		});
</script>
