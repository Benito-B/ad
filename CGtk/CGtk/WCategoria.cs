using System;
using System.Collections.Generic;
using System.Diagnostics;
using CGtk.Articulo;
using Gdk;

namespace CGtk {
    public partial class WCategoria : Gtk.Window {
        public WCategoria() :
                base(Gtk.WindowType.Toplevel) {
            Build();
            btExit.Clicked += (sender, e) => Destroy();
        }
    }
}
