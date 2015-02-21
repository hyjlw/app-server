<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
Ext.ns("Ext.Authority.books"); // 自定义一个命名空间
book = Ext.Authority.books; // 定义命名空间的别名
book = {
	all : ctx + '/book/all',// 加载所有
	save : ctx + "/book/save",//保存
	del : ctx + "/book/del/",//删除
	pageSize : 20, // 每页显示的记录数
	OPENED : eval('(${fields.opened==null?"{}":fields.opened})'),
	SPTYPE : eval('(${fields.spTypes==null?"{}":fields.spTypes})')
};

/** 改变页的combo */
book.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					book.pageSize = parseInt(comboBox.getValue());
					book.bbar.pageSize = parseInt(comboBox.getValue());
					book.store.baseParams.limit = book.pageSize;
					book.store.baseParams.start = 0;
					book.store.load();
				}
			}
		});
// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
book.pageSize = parseInt(book.pageSizeCombo.getValue());
/** 基本信息-数据源 */
book.store = new Ext.data.Store({
			autoLoad : true,
			remoteSort : true,
			baseParams : {
				start : 0,
				limit : book.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : book.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'name', 'edition', 'isbn', 'author', 'translator', 'publisher', 'publishDate', 'price', 'remarks', 'createDate']),
			listeners : {
				'load' : function(store, records, options) {
					book.alwaysFun();
				}
			}
		});
/** 基本信息-选择模式 */
book.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					book.deleteAction.enable();
					book.editAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					book.alwaysFun();
				}
			}
		});
/** 基本信息-数据列 */
book.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [book.selModel, {
						hidden : true,
						header : '编号',
						dataIndex : 'id'
					}, {
						header : '书名',
						dataIndex : 'name'
					}, {
						header : '版本',
						dataIndex : 'edition'
					}, {
						header : 'ISBN',
						dataIndex : 'isbn'
					}, {
						header : '作者',
						dataIndex : 'author'
					}, {
						header : '译者',
						dataIndex : 'translator'
					}, {
						header : '出版社',
						dataIndex : 'publisher'
					}, {
						header : '出版日期',
						dataIndex : 'publishDate'
					}, {
						header : '价格',
						dataIndex : 'price'
					}, {
						header : '备注',
						dataIndex : 'remarks'
					}]
		});
/** 新建 */
book.addAction = new Ext.Action({
			text : '添加',
			iconCls : 'module_add',
			handler : function() {
				book.addWindow.setIconClass('module_add'); // 设置窗口的样式
				book.addWindow.setTitle('添加图书'); // 设置窗口的名称
				book.addWindow.show().center(); // 显示窗口
				book.formPanel.getForm().reset(); // 清空表单里面的元素的值.
			}
		});
/** 编辑 */
book.editAction = new Ext.Action({
			text : '编辑',
			iconCls : 'module_edit',
			disabled : true,
			handler : function() {
				var record = book.grid.getSelectionModel().getSelected();
				book.addWindow.setIconClass('module_edit'); // 设置窗口的样式
				book.addWindow.setTitle('编辑图书'); // 设置窗口的名称
				book.addWindow.show().center();
				book.formPanel.getForm().reset();
				book.formPanel.getForm().loadRecord(record);
			}
		});
/** 删除 */
book.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'module_delete',
			disabled : true,
			handler : function() {
				book.delFun();
			}
		});
/** 查询 */
book.searchField = new Ext.ux.form.SearchField({
			store : book.store,
			paramName : 'name',
			emptyText : '请输入名称',
			style : 'margin-left: 5px;'
		});
/** 顶部工具栏 */
book.tbar = [book.addAction, '-', book.editAction, '-',
		book.deleteAction, '-', book.searchField];
/** 底部工具条 */
book.bbar = new Ext.PagingToolbar({
			pageSize : book.pageSize,
			store : book.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', book.pageSizeCombo]
		});
/** 基本信息-表格 */
book.grid = new Ext.grid.GridPanel({
			// title : '模块列表',
			store : book.store,
			colModel : book.colModel,
			selModel : book.selModel,
			tbar : book.tbar,
			bbar : book.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			// autoExpandColumn :'bookDesc',
			stripeRows : true,
			listeners : {},
			viewConfig : {}
		});
/** 基本信息-详细信息的form */
book.formPanel = new Ext.form.FormPanel({
			frame : false,
			title : '图书信息',
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
						maxLength : 128,
						allowBlank : false,
						name : 'name',
						anchor : '99%'
					}, {
						fieldLabel : '版本',
						maxLength : 128,
						allowBlank : false,
						name : 'edition',
						anchor : '99%'
					}, {
						fieldLabel : 'ISBN',
						maxLength : 128,
						allowBlank : false,
						name : 'isbn',
						anchor : '99%'
					}, {
						fieldLabel : '作者',
						maxLength : 128,
						allowBlank : false,
						name : 'author',
						anchor : '99%'
					}, {
						fieldLabel : '译者',
						maxLength : 128,
						name : 'translator',
						anchor : '99%'
					}, {
						fieldLabel : '出版社',
						maxLength : 128,
						allowBlank : false,
						name : 'publisher',
						anchor : '99%'
					}, {
						fieldLabel : '出版日期',
						maxLength : 128,
						allowBlank : false,
						name : 'publishDate',
						anchor : '99%'
					}, {
						fieldLabel : '价格',
						maxLength : 128,
						allowBlank : false,
						xtype : 'numberfield',
						name : 'price',
						anchor : '99%'
					}, {
						xtype : 'textarea',
						fieldLabel : '备注',
						maxLength : 512,
						height : 70,
						name : 'remarks',
						anchor : '99%'
					}]
		});
/** 编辑新建窗口 */
book.addWindow = new Ext.Window({
			layout : 'fit',
			width : 500,
			height : 420,
			closeAction : 'hide',
			plain : true,
			modal : true,
			resizable : true,
			items : [book.formPanel],
			buttons : [{
						text : '保存',
						handler : function() {
							book.saveFun();
						}
					}, {
						text : '重置',
						handler : function() {
							var form = book.formPanel.getForm();
							var id = form.findField("id").getValue();
							form.reset();
							if (id != '')
								form.findField("id").setValue(id);
						}
					}]
		});
book.alwaysFun = function() {
	Share.resetGrid(book.grid);
	book.deleteAction.disable();
	book.editAction.disable();
};
book.saveFun = function() {
	var form = book.formPanel.getForm();
	if (!form.isValid()) {
		return;
	}
	// 发送请求
	Share.AjaxRequest({
				url : book.save,
				params : form.getValues(),
				callback : function(json) {
					book.addWindow.hide();
					book.alwaysFun();
					book.store.reload();
				}
			});
};
book.delFun = function() {
	var record = book.grid.getSelectionModel().getSelected();
	Ext.Msg.confirm('提示', '你真的要删除选中的图书吗?', function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
								url : book.del + record.data.id,
								callback : function(json) {
									book.alwaysFun();
									book.store.reload();
								}
							});
				}
			});
};
book.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [book.grid]
		});
</script>
