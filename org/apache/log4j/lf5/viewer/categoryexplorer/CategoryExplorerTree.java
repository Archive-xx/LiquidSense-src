package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreePath;

public class CategoryExplorerTree extends JTree {
   private static final long serialVersionUID = 8066257446951323576L;
   protected CategoryExplorerModel _model;
   protected boolean _rootAlreadyExpanded = false;

   public CategoryExplorerTree(CategoryExplorerModel model) {
      super(model);
      this._model = model;
      this.init();
   }

   public CategoryExplorerTree() {
      CategoryNode rootNode = new CategoryNode("Categories");
      this._model = new CategoryExplorerModel(rootNode);
      this.setModel(this._model);
      this.init();
   }

   public CategoryExplorerModel getExplorerModel() {
      return this._model;
   }

   public String getToolTipText(MouseEvent e) {
      try {
         return super.getToolTipText(e);
      } catch (Exception var3) {
         return "";
      }
   }

   protected void init() {
      this.putClientProperty("JTree.lineStyle", "Angled");
      CategoryNodeRenderer renderer = new CategoryNodeRenderer();
      this.setEditable(true);
      this.setCellRenderer(renderer);
      CategoryNodeEditor editor = new CategoryNodeEditor(this._model);
      this.setCellEditor(new CategoryImmediateEditor(this, new CategoryNodeRenderer(), editor));
      this.setShowsRootHandles(true);
      this.setToolTipText("");
      this.ensureRootExpansion();
   }

   protected void expandRootNode() {
      if (!this._rootAlreadyExpanded) {
         this._rootAlreadyExpanded = true;
         TreePath path = new TreePath(this._model.getRootCategoryNode().getPath());
         this.expandPath(path);
      }
   }

   protected void ensureRootExpansion() {
      this._model.addTreeModelListener(new TreeModelAdapter() {
         public void treeNodesInserted(TreeModelEvent e) {
            CategoryExplorerTree.this.expandRootNode();
         }
      });
   }
}
