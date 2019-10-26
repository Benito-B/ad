using System;
using System.Collections.Generic;
using CutreGest.model;
using System.Data;
using CutreGest.logic;
using CSerpisAd;
namespace CutreGest.dao {
    public static class MovementDAO {

        private static string selectQuery = "Select s.*, a.name from stock_movements s, articles a " +
        	"where s.articleId = a.id order by date desc";

        public static ICollection<Movement> GetAll() {
            ICollection<Movement> movements = new List<Movement>();

            IDbCommand comm = DbConn.Instance.Connection.CreateCommand();
            comm.CommandText = selectQuery;
            IDataReader dataReader = comm.ExecuteReader();
            while (dataReader.Read())
                movements.Add(new Movement(new Article((string)dataReader["name"]),
                    (string)dataReader["Type"], (int)dataReader["ammount"], (DateTime)dataReader["date"]));
            dataReader.Close();
            return movements;
        }


        private static string insertQuery = "insert into stock_movements(articleId, type, ammount) " +
        	"values(@articleId, @type, @ammount)";

        public static void Save(Movement m) {
            IDbCommand dbCommand = DbConn.Instance.Connection.CreateCommand();
            dbCommand.CommandText = insertQuery;
            DbCommandHelper.AddParameter(dbCommand, "@articleId", m.Article.Id);
            DbCommandHelper.AddParameter(dbCommand, "@type", m.Type);
            DbCommandHelper.AddParameter(dbCommand, "@ammount", m.Ammount);
            dbCommand.ExecuteNonQuery();
        }
    }
}
