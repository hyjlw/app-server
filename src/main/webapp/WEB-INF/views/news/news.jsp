<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.news"); // 自定义一个命名空间
news = Ext.Authority.news; // 定义命名空间的别名
news = {
	all : ctx + '/news/all',// 加载所有
	save : ctx + "/news/save",//保存
	del : ctx + "/news/del/",//删除
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})'),
	pagesizes:eval('(${fields.pagesizes==null?"{}":fields.pagesizes})'),
	PARENTS : eval('(${fields.parents==null?"{}":fields.parents})'),
	pageSize : 20 // 每页显示的记录数
};
/** 改变页的combo */
news.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					news.pageSize = parseInt(comboBox.getValue());
					news.bbar.pageSize = parseInt(comboBox.getValue());
					news.store.baseParams.limit = news.pageSize;
					news.store.baseParams.start = 0;
					news.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
news.pageSize = parseInt(news.pageSizeCombo.getValue());
/** 基本信息-数据源 */
news.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : news.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : news.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'parent_id', 'title', 'domain', 'encoding', 'rss_encoding',
					'regex_img', 'regex_content', 'regex_content_filter', 'regex_url_node',
					'regex_url_node_full', 'rss_url', 'is_mobile', 'path_node', 
					'path_link', 'path_description', 'path_thumbnail', 'enabled', 'sort']),
			listeners : {
				'load' : function(store, records, options) {
					news.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
news.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					news.deleteAction.enable();
					news.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					news.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
news.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 240
			},
			columns : [news.selModel, {
						hidden : true,
						header : '字段ID',
						dataIndex : 'id'
					}, {
						// (0:禁用;1启用)
						header : '上级',
						dataIndex : 'parent_id',
						renderer : function(v) {
							return Share.map(v,news.PARENTS , '');
						}
					}, {
						header : '标题',
						dataIndex : 'title'
					}, {
						header : '域名',
						dataIndex : 'domain'
					}, {
						header : 'encoding',
						dataIndex : 'encoding'
					}, {
						header : 'rss_encoding',
						dataIndex : 'rss_encoding'
					}, {
						header : 'regex_img',
						dataIndex : 'regex_img',
						renderer : function(v) {
							return Share.htmlEncode(v);
						}
					}, {
						header : 'regex_content',
						dataIndex : 'regex_content',
						renderer : function(v) {
							return Share.htmlEncode(v);
						}
					}, {
						header : 'regex_content_filter',
						dataIndex : 'regex_content_filter',
						renderer : function(v) {
							return Share.htmlEncode(v);
						}
					}, {
						header : 'regex_url_node',
						dataIndex : 'regex_url_node',
						renderer : function(v) {
							return Share.htmlEncode(v);
						}
					}, {
						header : 'regex_url_node_full',
						dataIndex : 'regex_url_node_full',
						renderer : function(v) {
							return Share.htmlEncode(v);
						}
					}, {
						header : 'rss_url',
						dataIndex : 'rss_url'
					}, {
						header : 'is_mobile',
						dataIndex : 'is_mobile'
					}, {
						header : 'path_node',
						dataIndex : 'path_node'
					}, {
						header : 'path_link',
						dataIndex : 'path_link'
					}, {
						header : 'path_description',
						dataIndex : 'path_description'
					}, {
						header : 'path_thumbnail',
						dataIndex : 'path_thumbnail'
					}, {
						// (0:禁用;1启用)
						header : '是否启用',
						dataIndex : 'enabled',
						renderer : function(v) {
							return Share.map(v,news.ENABLED , '');
						}
					}, {
						header : '显示顺序',
						dataIndex : 'sort'
					}]
		});
/** 新建 */
news.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'field_add',
			handler : function() {
				news.addWindow.setIconClass('field_add'); // 设置窗口的样式
				news.addWindow.setTitle('新建新闻'); // 设置窗口的名称
				news.addWindow.show().center(); // 显示窗口
				news.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				news.enabledCombo.clearValue();
			}
		});
/** 编辑 */
news.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'field_edit',
			disabled : true,
			handler : function() {
				var record = news.grid.getSelectionModel().getSelected();
				news.addWindow.setIconClass('field_edit'); // 设置窗口的样式
				news.addWindow.setTitle('编辑新闻'); // 设置窗口的名称
				news.addWindow.show().center();
				news.formPanel.getForm().reset();
				news.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
news.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'field_delete',
			disabled : true,
			handler : function() {
				news.delFun();
			}
		});
/** 查询 */
news.searchField = new Ext.ux.form.SearchField({
			store : news.store,
			paramName : 'name',
			emptyText : '请输入字段名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
news.tbar = [news.addAction, '-', news.editAction, '-',
		news.deleteAction, '-', news.searchField];
/** 底部工具条 */
news.bbar = new Ext.PagingToolbar({
			pageSize : news.pageSize,
			store : news.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', news.pageSizeCombo]
		});
/** 基本信息-表格 */
news.grid = new Ext.grid.GridPanel({
			store : news.store,
			colModel : news.colModel,
			selModel : news.selModel,
			tbar : news.tbar,
			bbar : news.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
news.enabledCombo = new Ext.form.ComboBox({
			fieldLabel : '是否启用',
			hiddenName : 'enabled',
			name : 'enabled',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['v', 't'],
						data : Share.map2Ary(news.ENABLED)
					}),
			valueField : 'v',
			displayField : 't',
			allowBlank : false,
			editable : false,
			anchor : '99%'
		});
news.parentCombo = new Ext.form.ComboBox({
	fieldLabel : '上级',
	hiddenName : 'parent_id',
	name : 'parent_id',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(news.PARENTS)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : true,
	editable : false,
	anchor : '99%'
});
/** 基本信息-详细信息的form */
news.formPanel = new Ext.form.FormPanel({
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
					}, news.parentCombo, {
						fieldLabel : '标题',
						maxLength : 64,
						allowBlank : false,
						name : 'title',
						anchor : '99%'
					},{
						fieldLabel : '域名',
						maxLength : 128,
						name : 'domain',
						anchor : '99%'
					},{
						fieldLabel : 'encoding',
						maxLength : 10,
						name : 'encoding',
						anchor : '99%'
					},{
						fieldLabel : 'rss_encoding',
						maxLength : 10,
						name : 'rss_encoding',
						anchor : '99%'
					}, {
						fieldLabel : 'regex_img',
						maxLength : 512,
						name : 'regex_img',
						anchor : '99%'
					},{
						fieldLabel : 'regex_content',
						maxLength : 512,
						name : 'regex_content',
						anchor : '99%'
					}, {
						fieldLabel : 'regex_content_filter',
						maxLength : 512,
						name : 'regex_content_filter',
						anchor : '99%'
					},{
						fieldLabel : 'regex_url_node',
						maxLength : 512,
						name : 'regex_url_node',
						anchor : '99%'
					}, {
						fieldLabel : 'regex_url_node_full',
						maxLength : 512,
						name : 'regex_url_node_full',
						anchor : '99%'
					},{
						fieldLabel : 'rss_url',
						maxLength : 512,
						name : 'rss_url',
						anchor : '99%'
					}, {
						fieldLabel : 'is_mobile',
						maxLength : 10,
						name : 'is_mobile',
						anchor : '99%'
					},{
						fieldLabel : 'path_node',
						maxLength : 512,
						name : 'path_node',
						anchor : '99%'
					}, {
						fieldLabel : 'path_link',
						maxLength : 128,
						name : 'path_link',
						anchor : '99%'
					},{
						fieldLabel : 'path_description',
						maxLength : 512,
						name : 'path_description',
						anchor : '99%'
					}, {
						fieldLabel : 'path_thumbnail',
						maxLength : 512,
						name : 'path_thumbnail',
						anchor : '99%'
					}, news.enabledCombo, {
						fieldLabel : '显示顺序',
						xtype : 'numberfield',
						maxLength : 10,
						allowBlank : false,
						name : 'sort',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
news.addWindow = new Ext.Window({
			layout : 'fit',
			width : 420,
			height : 580,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [news.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							news.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = news.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
news.alwaysFun = function() {
	Share.resetGrid(news.grid);
	news.deleteAction.disable();
	news.editAction.disable();
};
news.saveFun = function() {
	var form = news.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : news.save,
				params : form.getValues(),
				callback : function(json) {
					news.addWindow.hide();
					news.alwaysFun();
					news.store.reload();
				}
			});
};
news.delFun = function() {
	var record = news.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '确定要删除这条记录吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : news.del + record.data.id,
								callback : function(json) {
									news.alwaysFun();
									news.store.reload();
								}
							});
				}
			});
};
news.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [news.grid]
		});
</script>
