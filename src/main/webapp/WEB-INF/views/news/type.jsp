<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.type"); // 自定义一个命名空间
type = Ext.Authority.type; // 定义命名空间的别名
type = {
	all : ctx + '/type/all',// 加载所有
	save : ctx + "/type/save",//保存
	del : ctx + "/type/del/",//删除
	ENABLED : eval('(${fields.enabled==null?"{}":fields.enabled})'),
	pagesizes:eval('(${fields.pagesizes==null?"{}":fields.pagesizes})'),
	pageSize : 20 // 每页显示的记录数
};
/** 改变页的combo */
type.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					type.pageSize = parseInt(comboBox.getValue());
					type.bbar.pageSize = parseInt(comboBox.getValue());
					type.store.baseParams.limit = type.pageSize;
					type.store.baseParams.start = 0;
					type.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
type.pageSize = parseInt(type.pageSizeCombo.getValue());
/** 基本信息-数据源 */
type.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : type.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : type.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'enabled', 'isSubscribe', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					type.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
type.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					type.deleteAction.enable();
					type.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					type.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
type.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 240
			},
			columns : [type.selModel, {
						hidden : true,
						header : '字段ID',
						dataIndex : 'id'
					}, {
						header : '名称',
						dataIndex : 'name'
					}, {
						header : '启用',
						dataIndex : 'enabled',
						renderer : function(v) {
							return Share.map(v,type.ENABLED , '');
						}
					}, {
						header : '订阅',
						dataIndex : 'isSubscribe',
						renderer : function(v) {
							return Share.map(v,type.ENABLED , '');
						}
					}, {
						header : '日期',
						dataIndex : 'createDate'
					}]
		});
/** 新建 */
type.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'field_add',
			handler : function() {
				type.addWindow.setIconClass('field_add'); // 设置窗口的样式
				type.addWindow.setTitle('新建类型'); // 设置窗口的名称
				type.addWindow.show().center(); // 显示窗口
				type.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				type.enabledCombo.clearValue();
			}
		});
/** 编辑 */
type.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'field_edit',
			disabled : true,
			handler : function() {
				var record = type.grid.getSelectionModel().getSelected();
				type.addWindow.setIconClass('field_edit'); // 设置窗口的样式
				type.addWindow.setTitle('编辑类型'); // 设置窗口的名称
				type.addWindow.show().center();
				type.formPanel.getForm().reset();
				type.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
type.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'field_delete',
			disabled : true,
			handler : function() {
				type.delFun();
			}
		});
/** 查询 */
type.searchField = new Ext.ux.form.SearchField({
			store : type.store,
			paramName : 'name',
			emptyText : '请输入字段名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
type.tbar = [type.addAction, '-', type.editAction, '-',
		type.deleteAction, '-', type.searchField];
/** 底部工具条 */
type.bbar = new Ext.PagingToolbar({
			pageSize : type.pageSize,
			store : type.store,
			displayInfo : true,
			items : ['-', '&nbsp;', type.pageSizeCombo]
		});
/** 基本信息-表格 */
type.grid = new Ext.grid.GridPanel({
			store : type.store,
			colModel : type.colModel,
			selModel : type.selModel,
			tbar : type.tbar,
			bbar : type.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
		

type.enabledCombo = new Ext.form.ComboBox({
	fieldLabel : '是否启用',
	hiddenName : 'enabled',
	name : 'enabled',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(type.ENABLED)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

type.subscribeCombo = new Ext.form.ComboBox({
	fieldLabel : '是否订阅',
	hiddenName : 'isSubscribe',
	name : 'isSubscribe',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['v', 't'],
				data : Share.map2Ary(type.ENABLED)
			}),
	valueField : 'v',
	displayField : 't',
	allowBlank : false,
	editable : false,
	anchor : '99%'
});

/** 基本信息-详细信息的form */
type.formPanel = new Ext.form.FormPanel({
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
					}, type.enabledCombo, type.subscribeCombo
					]
		});
/** 编辑新建窗口 */
type.addWindow = new Ext.Window({
			layout : 'fit',
			width : 420,
			height : 240,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [type.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							type.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = type.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
type.alwaysFun = function() {
	Share.resetGrid(type.grid);
	type.deleteAction.disable();
	type.editAction.disable();
};
type.saveFun = function() {
	var form = type.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : type.save,
				params : form.getValues(),
				callback : function(json) {
					type.addWindow.hide();
					type.alwaysFun();
					type.store.reload();
				}
			});
};
type.delFun = function() {
	var record = type.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '确定要删除这条记录吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : type.del + record.data.id,
								callback : function(json) {
									type.alwaysFun();
									type.store.reload();
								}
							});
				}
			});
};
type.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [type.grid]
		});
</script>
