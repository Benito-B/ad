using System;
using CutreGest.model;
using CutreGest.view.windows;
using Gtk;
using CutreGest.dao;
namespace CutreGest.logic {
    public static class Warehouse {

        public static bool DoMovement(Movement m, Window w) {
            //Si no hay stock suficiente, anular el movimiento
            if(m.Article.Stock.CurrentStock < m.Ammount) {
                DialogHelper.ShowDialog("Error de stock", "Stock insuficiente\n" +
                    "Stock actual: " + m.Article.Stock.CurrentStock, w,
                    DialogFlags.Modal | DialogFlags.DestroyWithParent, "Volver", ResponseType.Close);
                return false;
            }
            //Si después del movimiento quedan menos del mínimo
            if ((m.Article.Stock.CurrentStock - m.Ammount) < m.Article.Stock.MinStock && 
                m.Type.Equals(Movement.M_OUT))
                DialogHelper.ShowDialog("Advertencia de stock", "El stock actual de " +
                m.Article.Name + " \nha alcanzado el mínimo establecido", w,
                    DialogFlags.Modal | DialogFlags.DestroyWithParent, "Aceptar", ResponseType.Close);
            //Si después del movimiento el stock sobrepasa el límite máximo
            if((m.Article.Stock.CurrentStock + m.Ammount) > m.Article.Stock.MaxStock && 
                m.Type.Equals(Movement.M_IN))
                DialogHelper.ShowDialog("Aviso de stock", "El stock actual de " +
                m.Article.Name + " \nha superado el máximo establecido", w,
                    DialogFlags.Modal | DialogFlags.DestroyWithParent, "Aceptar", ResponseType.Close);

            switch (m.Type) {
                case Movement.M_IN:
                    m.Article.Stock.CurrentStock += m.Ammount;
                    break;
                case Movement.M_OUT:
                    m.Article.Stock.CurrentStock -= m.Ammount;
                    break;
            }
            StockDAO.Update(m.Article.Stock);
            MovementDAO.Save(m);
            return true;
        }
    }
}
