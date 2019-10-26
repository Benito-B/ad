using System;
using System.Data;
using CSerpisAd;
using CutreGest.logic;
using CutreGest.model;

namespace CutreGest.dao {
    public static class StockDAO {

        private static string getQuery = "select * from stocks where articleid = @articleid";

        public static Stock GetStock(ulong id) {
            IDbConnection dbConn = DbConn.Instance.Connection;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = getQuery;
            DbCommandHelper.AddParameter(dbCommand, "@articleid", id);
            IDataReader dataReader = dbCommand.ExecuteReader();
            dataReader.Read();
            dataReader.Close();
            return new Stock((ulong)dataReader["articleid"], (int)dataReader["currentStock"],
                    (int)dataReader["minStock"], (int)dataReader["maxStock"]);
        }


        private static string insertQuery = "insert into stocks(articleId, currentStock, minStock, maxStock) " +
        	"values (@articleId, @currentStock, @minStock, @maxStock)";

        public static void Insert(Stock s) {
            IDbConnection dbConn = DbConn.Instance.Connection;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = insertQuery;
            DbCommandHelper.AddParameter(dbCommand, "@articleId", s.ArticleId);
            DbCommandHelper.AddParameter(dbCommand, "@currentStock", s.CurrentStock);
            DbCommandHelper.AddParameter(dbCommand, "@minStock", s.MinStock);
            DbCommandHelper.AddParameter(dbCommand, "@maxStock", s.MaxStock);
            dbCommand.ExecuteNonQuery();
        }

        private static string updateQuery = "update stocks set currentStock = @currentStock, " +
            "minStock = @minStock, maxStock = @maxStock where articleId = @articleId";

        public static void Update(Stock s) {
            IDbConnection dbConn = DbConn.Instance.Connection;
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = updateQuery;
            DbCommandHelper.AddParameter(dbCommand, "@currentStock", s.CurrentStock);
            DbCommandHelper.AddParameter(dbCommand, "@minStock", s.MinStock);
            DbCommandHelper.AddParameter(dbCommand, "@maxStock", s.MaxStock);
            DbCommandHelper.AddParameter(dbCommand, "@articleId", s.ArticleId);
            dbCommand.ExecuteNonQuery();
        }

    }
}
