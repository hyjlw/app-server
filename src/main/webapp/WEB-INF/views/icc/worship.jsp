<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.worships"); // 自定义一个命名空间
worship = Ext.Authority.worships; // 定义命名空间的别名
worship = {
	all : ctx + '/worship/all',// 加载所有
	save : ctx + "/worship/save",//保存
	del : ctx + "/worship/del/",//删除
	pageSize : 20, // 每页显示的记录数
	OPENED : eval('(${fields.opened==null?"{}":fields.opened})'),
	SPTYPE : eval('(${fields.spTypes==null?"{}":fields.spTypes})')
};

/** 改变页的combo */
worship.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					worship.pageSize = parseInt(comboBox.getValue());
					worship.bbar.pageSize = parseInt(comboBox.getValue());
					worship.store.baseParams.limit = worship.pageSize;
					worship.store.baseParams.start = 0;
					worship.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
worship.pageSize = parseInt(worship.pageSizeCombo.getValue());
/** 基本信息-数据源 */
worship.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : worship.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : worship.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'content', 'remark']),
			listeners : {
				'load' : function(store, records, options) {
					worship.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
worship.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					worship.deleteAction.enable();
					worship.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					worship.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
worship.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [worship.selModel, {
						hidden : true,
						header : '编号',
						dataIndex : 'id'
					}, {
						header : '名称',
						dataIndex : 'name'
					}, {
						header : '内容',
						dataIndex : 'content'
					}, {
						header : '备注',
						dataIndex : 'remark'
					}]
		});
/** 新建 */
worship.addAction = new Ext.Action({
			text : '新建',
			iconCls : 'module_add',
			handler : function() {
				worship.addWindow.setIconClass('module_add'); // 设置窗口的样式
				worship.addWindow.setTitle('新建敬拜歌'); // 设置窗口的名称
				worship.addWindow.show().center(); // 显示窗口
				worship.formPanel.getForm().reset(); // 清空表单里面的元素的值.
			}
		});
/** 编辑 */
worship.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = worship.grid.getSelectionModel().getSelected();
				worship.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				worship.addWindow.setTitle('编辑敬拜歌'); // 设置窗口的名称
				worship.addWindow.show().center();
				worship.formPanel.getForm().reset();
				worship.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
worship.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				worship.delFun();
			}
		});
/** 查询 */
worship.searchField = new Ext.ux.form.SearchField({
			store : worship.store,
			paramName : 'name',
			emptyText : '请输入名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
worship.tbar = [worship.addAction, '-', worship.editAction, '-',
		worship.deleteAction, '-', worship.searchField];
/** 底部工具条 */
worship.bbar = new Ext.PagingToolbar({
			pageSize : worship.pageSize,
			store : worship.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', worship.pageSizeCombo]
		});
/** 基本信息-表格 */
worship.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : worship.store,
			colModel : worship.colModel,
			selModel : worship.selModel,
			tbar : worship.tbar,
			bbar : worship.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			// autoExpandColumn :'worshipDesc',
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
/** 基本信息-详细信息的form */
worship.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '敬拜歌信息',
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
						maxLength : 100,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					}, {
						xtype : 'textarea',
						fieldLabel : '内容',
						maxLength : 8192,
						height : 380,
						name : 'content',
						allowBlank : false,
						anchor : '99%'
					}, {
						xtype : 'textarea',
						fieldLabel : '备注',
						maxLength : 2000,
						height : 50,
						name : 'remark',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
worship.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 580,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [worship.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							worship.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = worship.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
worship.alwaysFun = function() {
	Share.resetGrid(worship.grid);
	worship.deleteAction.disable();
	worship.editAction.disable();
};
worship.saveFun = function() {
	var form = worship.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : worship.save,
				params : form.getValues(),
				callback : function(json) {
					worship.addWindow.hide();
					worship.alwaysFun();
					worship.store.reload();
				}
			});
};
worship.delFun = function() {
	var record = worship.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中的敬拜歌吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : worship.del + record.data.id,
								callback : function(json) {
									worship.alwaysFun();
									worship.store.reload();
								}
							});
				}
			});
};
worship.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [worship.grid]
		});
</script>
