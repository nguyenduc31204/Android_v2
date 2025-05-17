package com.example.newspaper.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newspaper.database.dao.ArticleDao;
import com.example.newspaper.database.dao.UserDao;
import com.example.newspaper.models.Article;
import com.example.newspaper.models.Category;
import com.example.newspaper.models.Comment;
import com.example.newspaper.models.Emotion;
import com.example.newspaper.models.Notification;
import com.example.newspaper.models.ReadHistory;
import com.example.newspaper.models.SearchHistory;
import com.example.newspaper.models.User;
import com.example.newspaper.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Article.class, Comment.class, Emotion.class, Notification.class, ReadHistory.class,
                SearchHistory.class, User.class, Category.class
        },
        version = 3
)
@TypeConverters(Converters.class)
public abstract class DatabaseHandler extends RoomDatabase {

    private static DatabaseHandler instance;

    public abstract ArticleDao getArticleDao();
    public abstract UserDao userDao();

    public static DatabaseHandler getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseHandler.class, "news_paper_app_db")
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_2_3)
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users ADD COLUMN avatarUri TEXT");
        }
    };

    public static RoomDatabase.Callback roomCallBack = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase sqd){
            super.onCreate(sqd);

            new Thread(() -> {
                DatabaseHandler database = getInstance(null);

//                // Thêm admin user mẫu
                User admin = new User();
                admin.setEmail("ad@1.1");
                admin.setPasswordHash("111111");
                admin.setRole("admin");
                database.userDao().insert(admin);
            }).start();
        };
    };

    public static void closeDatabase() {
        if (instance != null && instance.isOpen()) {
            instance.close();
            instance = null;
        }
    }
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);
}
