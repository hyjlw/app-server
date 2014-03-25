<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.site"); // 自定义一个命名空间
site = Ext.Authority.site; // 定义命名空间的别名
site = {
	all : ctx + '/site/all',// 加载所有
	save : ctx + "/site/save",//保存
	del : ctx + "/site/del/",//删除
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})'),
	pagesizes:eval('(${fields.pagesizes==null?"{}":fields.pagesizes})'),
	pageSize : 20 // 每页显示的记录数
};
/** 改变页的combo */
site.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					site.pageSize = parseInt(comboBox.getValue());
					site.bbar.pageSize = parseInt(comboBox.getValue());
					site.store.baseParams.limit = site.pageSize;
					site.store.baseParams.start = 0;
					site.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
site.pageSize = parseInt(site.pageSizeCombo.getValue());
/** 基本信息-数据源 */
site.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : site.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : site.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'crawlerClass' 'siteUrl', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					site.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
site.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					site.deleteAction.enable();
					site.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					site.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
site.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 240
			},
			columns : [site.selModel, {
						hidden : true,
						header : '字段ID',
						dataIndex : 'id'
					}, {
						header : '名称',
						dataIndex : 'name'
					}, {
						header : 'Crawler类',
						dataIndex : 'crawlerClass'
					}, {
						header : '网址',
						dataIndex : 'siteUrl'
					}, {
						header : '日期',
						dataIndex : 'createDate'
					}]
		});
/** 新建 */
site.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'field_add',
			handler : function() {
				site.addWindow.setIconClass('field_add'); // 设置窗口的样式
				site.addWindow.setTitle('新建站点'); // 设置窗口的名称
				site.addWindow.show().center(); // 显示窗口
				site.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				site.enabledCombo.clearValue();
			}
		});
/** 编辑 */
site.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'field_edit',
			disabled : true,
			handler : function() {
				var record = site.grid.getSelectionModel().getSelected();
				site.addWindow.setIconClass('field_edit'); // 设置窗口的样式
				site.addWindow.setTitle('编辑站点'); // 设置窗口的名称
				site.addWindow.show().center();
				site.formPanel.getForm().reset();
				site.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
site.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'field_delete',
			disabled : true,
			handler : function() {
				site.delFun();
			}
		});
/** 查询 */
site.searchField = new Ext.ux.form.SearchField({
			store : site.store,
			paramName : 'name',
			emptyText : '请输入字段名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
site.tbar = [site.addAction, '-', site.editAction, '-',
		site.deleteAction, '-', site.searchField];
/** 底部工具条 */
site.bbar = new Ext.PagingToolbar({
			pageSize : site.pageSize,
			store : site.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', site.pageSizeCombo]
		});
/** 基本信息-表格 */
site.grid = new Ext.grid.GridPanel({
			store : site.store,
			colModel : site.colModel,
			selModel : site.selModel,
			tbar : site.tbar,
			bbar : site.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
/** 基本信息-详细信息的form */
site.formPanel = new Ext.form.FormPanel({
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
					}, {
						fieldLabel : '名称',
						maxLength : 64,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					}, {
						fieldLabel : 'Crawler类',
						maxLength : 64,
						allowBlank : false,
						name : 'crawlerClass',
						anchor : '99%'
					}, {
						fieldLabel : '站点',
						maxLength : 128,
						allowBlank : false,
						name : 'siteUrl',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
site.addWindow = new Ext.Window({
			layout : 'fit',
			width : 420,
			height : 180,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [site.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							site.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = site.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
site.alwaysFun = function() {
	Share.resetGrid(site.grid);
	site.deleteAction.disable();
	site.editAction.disable();
};
site.saveFun = function() {
	var form = site.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : site.save,
				params : form.getValues(),
				callback : function(json) {
					site.addWindow.hide();
					site.alwaysFun();
					site.store.reload();
				}
			});
};
site.delFun = function() {
	var record = site.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '确定要删除这条记录吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : site.del + record.data.id,
								callback : function(json) {
									site.alwaysFun();
									site.store.reload();
								}
							});
				}
			});
};
site.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [site.grid]
		});
</script>
