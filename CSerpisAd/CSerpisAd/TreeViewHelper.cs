using System;
using System.Collections;
using Gtk;
namespace CSerpisAd.GtkHelper {
    public class TreeViewHelper {
        public TreeViewHelper() {
        }

        public static void Fill(TreeView treeView, string[] properties, IEnumerable enumerable) {
            if (treeView.Model == null)
                init(treeView, properties);
            
            ListStore listStore = new ListStore(typeof(object));
            foreach (object obj in enumerable)
                listStore.AppendValues(obj);
            treeView.Model = listStore;
        }

        private static void init(TreeView treeView, string[] properties) {
            CellRendererText cellRenderer = new CellRendererText();
            foreach (string property in properties) {
                treeView.AppendColumn(property, cellRenderer,
                    new TreeCellDataFunc((tree_column, cell, tree_model, iter) => {
                        object obj = tree_model.GetValue(iter, 0);
                        object value = obj.GetType().GetProperty(property).GetValue(obj);
                        cellRenderer.Text = value.ToString();
                    })
                );
            }
        }


        public static object GetId(TreeView treeView) {
            if (!treeView.Selection.GetSelected(out TreeIter iter))
                return null;
            object obj = treeView.Model.GetValue(iter, 0);
            return obj.GetType().GetProperty("Id").GetValue(obj);
        }
    }
}
