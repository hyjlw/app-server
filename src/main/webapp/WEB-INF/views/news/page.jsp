<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.page"); // 自定义一个命名空间
page = Ext.Authority.page; // 定义命名空间的别名
page = {
	all : ctx + '/page/all',// 加载所有
	save : ctx + "/page/save",//保存
	del : ctx + "/page/del/",//删除
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})'),
	pagesizes:eval('(${fields.pagesizes==null?"{}":fields.pagesizes})'),
	sites:eval('(${fields.sites==null?"{}":fields.sites})'),
	types:eval('(${fields.types==null?"{}":fields.types})'),
	pageSize : 20 // 每页显示的记录数
};
/** 改变页的combo */
page.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					page.pageSize = parseInt(comboBox.getValue());
					page.bbar.pageSize = parseInt(comboBox.getValue());
					page.store.baseParams.limit = page.pageSize;
					page.store.baseParams.start = 0;
					page.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
page.pageSize = parseInt(page.pageSizeCombo.getValue());
/** 基本信息-数据源 */
page.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : page.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : page.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'siteId', 'typeId', 'name', 'storageFolder', 'webUrl', 'enabled', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					page.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
page.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					page.deleteAction.enable();
					page.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					page.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
page.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 240
			},
			columns : [page.selModel, {
						hidden : true,
						header : '字段ID',
						dataIndex : 'id'
					}, {
						header : '站点',
						dataIndex : 'siteId',
						renderer : function(v) {
							return Share.map(v,page.sites , '');
						}
					}, {
						header : '类型',
						dataIndex : 'typeId',
						renderer : function(v) {
							return Share.map(v,page.types , '');
						}
					}, {
						header : '名称',
						dataIndex : 'name'						
					}, {
						header : '存储路径',
						dataIndex : 'storageFolder'						
					}, {
						header : '地址',
						dataIndex : 'webUrl'						
					}, {
						header : '启用',
						dataIndex : 'enabled',
						renderer : function(v) {
							return Share.map(v, page.ENABLED , '');
						}
					}, {
						header : '日期',
						dataIndex : 'createDate'
					}]
		});
/** 新建 */
page.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'field_add',
			handler : function() {
				page.addWindow.setIconClass('field_add'); // 设置窗口的样式
				page.addWindow.setTitle('新建页'); // 设置窗口的名称
				page.addWindow.show().center(); // 显示窗口
				page.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				page.enabledCombo.clearValue();
			}
		});
/** 编辑 */
page.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'field_edit',
			disabled : true,
			handler : function() {
				var record = page.grid.getSelectionModel().getSelected();
				page.addWindow.setIconClass('field_edit'); // 设置窗口的样式
				page.addWindow.setTitle('编辑页'); // 设置窗口的名称
				page.addWindow.show().center();
				page.formPanel.getForm().reset();
				page.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
page.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'field_delete',
			disabled : true,
			handler : function() {
				page.delFun();
			}
		});
/** 查询 */
page.searchField = new Ext.ux.form.SearchField({
			store : page.store,
			paramName : 'name',
			emptyText : '请输入字段名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
page.tbar = [page.addAction, '-', page.editAction, '-',
		page.deleteAction, '-', page.searchField];
/** 底部工具条 */
page.bbar = new Ext.PagingToolbar({
			pageSize : page.pageSize,
			store : page.store,
			displayInfo : true,
			items : ['-', '&nbsp;', page.pageSizeCombo]
		});
/** 基本信息-表格 */
page.grid = new Ext.grid.GridPanel({
			store : page.store,
			colModel : page.colModel,
			selModel : page.selModel,
			tbar : page.tbar,
			bbar : page.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
		

page.siteCombo = new Ext.form.ComboBox({
	fieldLabel : '站点',
	hiddenName : 'siteId',
	name : 'siteId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(page.sites)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

page.typeCombo = new Ext.form.ComboBox({
	fieldLabel : '类型',
	hiddenName : 'typeId',
	name : 'typeId',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(page.types)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

page.enabledCombo = new Ext.form.ComboBox({
	fieldLabel : '是否启用',
	hiddenName : 'enabled',
	name : 'enabled',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(page.ENABLED)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

/** 基本信息-详细信息的form */
page.formPanel = new Ext.form.FormPanel({
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
					}, page.siteCombo, page.typeCombo,
					{
						fieldLabel : '名称',
						maxLength : 128,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					},
					{
						fieldLabel : '存储路径',
						maxLength : 128,
						allowBlank : false,
						name : 'storageFolder',
						anchor : '99%'
					},
					{
						fieldLabel : '地址',
						maxLength : 128,
						allowBlank : false,
						name : 'webUrl',
						anchor : '99%'
					}, page.enabledCombo
					]
		});
/** 编辑新建窗口 */
page.addWindow = new Ext.Window({
			layout : 'fit',
			width : 420,
			height : 300,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [page.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							page.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = page.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
page.alwaysFun = function() {
	Share.resetGrid(page.grid);
	page.deleteAction.disable();
	page.editAction.disable();
};
page.saveFun = function() {
	var form = page.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : page.save,
				params : form.getValues(),
				callback : function(json) {
					page.addWindow.hide();
					page.alwaysFun();
					page.store.reload();
				}
			});
};
page.delFun = function() {
	var record = page.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '确定要删除这条记录吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : page.del + record.data.id,
								callback : function(json) {
									page.alwaysFun();
									page.store.reload();
								}
							});
				}
			});
};
page.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [page.grid]
		});
</script>
