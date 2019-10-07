using System;
using Gtk;
using CGtk;
public partial class MainWindow : Gtk.Window {
    public MainWindow() : base(Gtk.WindowType.Toplevel) {
        Build();

        treeView.AppendColumn("id", new CellRendererText(), "text", 0);
        treeView.AppendColumn("nombre", new CellRendererText(), "text", 1);

        ListStore listStore = new ListStore(typeof(String), typeof(String));
        treeView.Model = listStore;
        listStore.AppendValues("1", "Cat1");

        addAction.Activated += (sender, e) => new WCategoria();
    }

    protected void OnDeleteEvent(object sender, DeleteEventArgs a) {
        Application.Quit();
        a.RetVal = true;
    }
}
