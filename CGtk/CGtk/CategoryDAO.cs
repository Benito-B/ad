using System;
using System.Collections.Generic;
using System.Data;
using CGtk.Articulo;
using CSerpisAd;

namespace CGtk {
    public class CategoryDAO {
        public CategoryDAO() {
        }
        /*
         * Categoría (id, nombre)
         */
        public static ICollection<Categoria> GetCategories() {
            ICollection<Categoria> categorias = new List<Categoria>();
            IDbConnection conn = DbConn.GetInstance().GetConnection();

            IDbCommand dbCommand = conn.CreateCommand();
            dbCommand.CommandText = "select * from categoria";
            IDataReader dataReader = dbCommand.ExecuteReader();

            while (dataReader.Read()) {
                categorias.Add(new Categoria((string)dataReader["nombre"], (ulong)dataReader["id"]));
            }
            dataReader.Close();
            return categorias;
        }

        public static Categoria GetCategory(ulong id) {
            IDbConnection dbConn = DbConn.GetInstance().GetConnection();
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = "select from categoria where id = @id";
            DbCommandHelper.AddParameter(dbCommand, "@id", id);
            IDataReader dataReader = dbCommand.ExecuteReader();
            dataReader.Read();
            dataReader.Close();
            return new Categoria((string)dataReader["nombre"], (ulong)dataReader["id"]);
        }
    }
}
