using System;
using System.Diagnostics;
using Gdk;

namespace CGtk {
    public partial class WCategoria : Gtk.Window {
        public WCategoria() :
                base(Gtk.WindowType.Toplevel) {
            this.Build();
            btExit.Clicked += (sender, e) => this.Destroy();
        }
    }
}
