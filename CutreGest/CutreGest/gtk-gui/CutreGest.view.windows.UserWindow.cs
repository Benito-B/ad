
// This file has been generated by the GUI designer. Do not modify.
namespace CutreGest.view.windows
{
	public partial class UserWindow
	{
		private global::Gtk.Table table1;

		private global::Gtk.Button btBack;

		private global::Gtk.Button btSubmit;

		private global::Gtk.CheckButton checkAdmin;

		private global::Gtk.Entry entryUsername;

		private global::Gtk.Entry entryUserPassword;

		private global::Gtk.Label label5;

		private global::Gtk.Label labelId;

		private global::Gtk.Label labelname;

		private global::Gtk.Label labelUserId;

		protected virtual void Build()
		{
			global::Stetic.Gui.Initialize(this);
			// Widget CutreGest.view.windows.UserWindow
			this.Name = "CutreGest.view.windows.UserWindow";
			this.Title = global::Mono.Unix.Catalog.GetString("Usuarios - Detalle");
			this.WindowPosition = ((global::Gtk.WindowPosition)(3));
			this.BorderWidth = ((uint)(12));
			this.Resizable = false;
			this.Decorated = false;
			this.DestroyWithParent = true;
			this.Gravity = ((global::Gdk.Gravity)(5));
			// Container child CutreGest.view.windows.UserWindow.Gtk.Container+ContainerChild
			this.table1 = new global::Gtk.Table(((uint)(5)), ((uint)(2)), false);
			this.table1.Name = "table1";
			this.table1.RowSpacing = ((uint)(6));
			this.table1.ColumnSpacing = ((uint)(6));
			// Container child table1.Gtk.Table+TableChild
			this.btBack = new global::Gtk.Button();
			this.btBack.CanFocus = true;
			this.btBack.Name = "btBack";
			this.btBack.UseUnderline = true;
			this.btBack.Label = global::Mono.Unix.Catalog.GetString("Cancelar");
			this.table1.Add(this.btBack);
			global::Gtk.Table.TableChild w1 = ((global::Gtk.Table.TableChild)(this.table1[this.btBack]));
			w1.TopAttach = ((uint)(4));
			w1.BottomAttach = ((uint)(5));
			w1.XOptions = ((global::Gtk.AttachOptions)(4));
			w1.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.btSubmit = new global::Gtk.Button();
			this.btSubmit.CanDefault = true;
			this.btSubmit.CanFocus = true;
			this.btSubmit.Name = "btSubmit";
			this.btSubmit.UseUnderline = true;
			this.btSubmit.Label = global::Mono.Unix.Catalog.GetString("Guardar");
			this.table1.Add(this.btSubmit);
			global::Gtk.Table.TableChild w2 = ((global::Gtk.Table.TableChild)(this.table1[this.btSubmit]));
			w2.TopAttach = ((uint)(4));
			w2.BottomAttach = ((uint)(5));
			w2.LeftAttach = ((uint)(1));
			w2.RightAttach = ((uint)(2));
			w2.XOptions = ((global::Gtk.AttachOptions)(4));
			w2.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.checkAdmin = new global::Gtk.CheckButton();
			this.checkAdmin.CanFocus = true;
			this.checkAdmin.Name = "checkAdmin";
			this.checkAdmin.Label = global::Mono.Unix.Catalog.GetString("Administrador");
			this.checkAdmin.DrawIndicator = true;
			this.checkAdmin.UseUnderline = true;
			this.table1.Add(this.checkAdmin);
			global::Gtk.Table.TableChild w3 = ((global::Gtk.Table.TableChild)(this.table1[this.checkAdmin]));
			w3.TopAttach = ((uint)(3));
			w3.BottomAttach = ((uint)(4));
			w3.LeftAttach = ((uint)(1));
			w3.RightAttach = ((uint)(2));
			w3.XOptions = ((global::Gtk.AttachOptions)(4));
			w3.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.entryUsername = new global::Gtk.Entry();
			this.entryUsername.CanFocus = true;
			this.entryUsername.Name = "entryUsername";
			this.entryUsername.IsEditable = true;
			this.entryUsername.InvisibleChar = '•';
			this.table1.Add(this.entryUsername);
			global::Gtk.Table.TableChild w4 = ((global::Gtk.Table.TableChild)(this.table1[this.entryUsername]));
			w4.TopAttach = ((uint)(1));
			w4.BottomAttach = ((uint)(2));
			w4.LeftAttach = ((uint)(1));
			w4.RightAttach = ((uint)(2));
			w4.XOptions = ((global::Gtk.AttachOptions)(4));
			w4.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.entryUserPassword = new global::Gtk.Entry();
			this.entryUserPassword.CanFocus = true;
			this.entryUserPassword.Name = "entryUserPassword";
			this.entryUserPassword.IsEditable = true;
			this.entryUserPassword.Visibility = false;
			this.entryUserPassword.InvisibleChar = '•';
			this.table1.Add(this.entryUserPassword);
			global::Gtk.Table.TableChild w5 = ((global::Gtk.Table.TableChild)(this.table1[this.entryUserPassword]));
			w5.TopAttach = ((uint)(2));
			w5.BottomAttach = ((uint)(3));
			w5.LeftAttach = ((uint)(1));
			w5.RightAttach = ((uint)(2));
			w5.XOptions = ((global::Gtk.AttachOptions)(4));
			w5.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.label5 = new global::Gtk.Label();
			this.label5.Name = "label5";
			this.label5.LabelProp = global::Mono.Unix.Catalog.GetString("Contraseña");
			this.table1.Add(this.label5);
			global::Gtk.Table.TableChild w6 = ((global::Gtk.Table.TableChild)(this.table1[this.label5]));
			w6.TopAttach = ((uint)(2));
			w6.BottomAttach = ((uint)(3));
			w6.XOptions = ((global::Gtk.AttachOptions)(4));
			w6.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.labelId = new global::Gtk.Label();
			this.labelId.Name = "labelId";
			this.labelId.LabelProp = global::Mono.Unix.Catalog.GetString("ID");
			this.table1.Add(this.labelId);
			global::Gtk.Table.TableChild w7 = ((global::Gtk.Table.TableChild)(this.table1[this.labelId]));
			w7.XOptions = ((global::Gtk.AttachOptions)(4));
			w7.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.labelname = new global::Gtk.Label();
			this.labelname.Name = "labelname";
			this.labelname.LabelProp = global::Mono.Unix.Catalog.GetString("Nombre");
			this.table1.Add(this.labelname);
			global::Gtk.Table.TableChild w8 = ((global::Gtk.Table.TableChild)(this.table1[this.labelname]));
			w8.TopAttach = ((uint)(1));
			w8.BottomAttach = ((uint)(2));
			w8.XOptions = ((global::Gtk.AttachOptions)(4));
			w8.YOptions = ((global::Gtk.AttachOptions)(4));
			// Container child table1.Gtk.Table+TableChild
			this.labelUserId = new global::Gtk.Label();
			this.labelUserId.Name = "labelUserId";
			this.labelUserId.LabelProp = global::Mono.Unix.Catalog.GetString("User ID");
			this.table1.Add(this.labelUserId);
			global::Gtk.Table.TableChild w9 = ((global::Gtk.Table.TableChild)(this.table1[this.labelUserId]));
			w9.LeftAttach = ((uint)(1));
			w9.RightAttach = ((uint)(2));
			w9.XOptions = ((global::Gtk.AttachOptions)(4));
			w9.YOptions = ((global::Gtk.AttachOptions)(4));
			this.Add(this.table1);
			if ((this.Child != null))
			{
				this.Child.ShowAll();
			}
			this.DefaultWidth = 269;
			this.DefaultHeight = 172;
			this.btSubmit.HasDefault = true;
			this.Show();
		}
	}
}