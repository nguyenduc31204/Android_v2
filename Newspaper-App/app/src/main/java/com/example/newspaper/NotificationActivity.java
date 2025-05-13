package com.example.newspaper;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.models.Article;
import com.example.newspaper.pojo.ArticleWithCategory;
import com.example.newspaper.ui.adapters.ArticleRecycleViewAdapter;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);

        List<ArticleWithCategory> articles = new ArrayList<>();
        List<ArticleViewItem> items = new ArrayList<>();

        articles.add(ArticleWithCategory.builder()
                        .article(Article.builder()
                                .title("Viện KSND Tối cao nhận định về vụ tai nạn dẫn đến nổ súng ở Vĩnh Long")
                                .summary("Ngày 3.5, Viện KSND Tối cao cho biết, thực hiện ý kiến chỉ đạo của Viện trưởng Viện KSND tối cao, các đơn vị nghiệp vụ Viện KSND Tối cao đã kiểm tra lại quyết định giải quyết khiếu nại số 55/QĐ-VKS ngày 14.3.2025 đã có hiệu lực pháp luật của Viện KSND tỉnh Vĩnh Long.")
                                .thumbnailUrl("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                                .publishedAt(Instant.now().minus(30, ChronoUnit.SECONDS))
                                .build())
                .build());

        articles.add(ArticleWithCategory.builder()
                .article(Article.builder()
                        .title("Viện KSND Tối cao nhận định về vụ tai nạn dẫn đến nổ súng ở Vĩnh Long")
                        .summary("Ngày 3.5, Viện KSND Tối cao cho biết, thực hiện ý kiến chỉ đạo của Viện trưởng Viện KSND tối cao, các đơn vị nghiệp vụ Viện KSND Tối cao đã kiểm tra lại quyết định giải quyết khiếu nại số 55/QĐ-VKS ngày 14.3.2025 đã có hiệu lực pháp luật của Viện KSND tỉnh Vĩnh Long.")
                        .thumbnailUrl("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                        .publishedAt(Instant.now().minus(30, ChronoUnit.SECONDS))
                        .build())
                .build());

        articles.add(ArticleWithCategory.builder()
                .article(Article.builder()
                        .title("Viện KSND Tối cao nhận định về vụ tai nạn dẫn đến nổ súng ở Vĩnh Long")
                        .summary("Ngày 3.5, Viện KSND Tối cao cho biết, thực hiện ý kiến chỉ đạo của Viện trưởng Viện KSND tối cao, các đơn vị nghiệp vụ Viện KSND Tối cao đã kiểm tra lại quyết định giải quyết khiếu nại số 55/QĐ-VKS ngày 14.3.2025 đã có hiệu lực pháp luật của Viện KSND tỉnh Vĩnh Long.")
                        .thumbnailUrl("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                        .publishedAt(Instant.now().minus(30, ChronoUnit.SECONDS))
                        .build())
                .build());

        articles.add(ArticleWithCategory.builder()
                .article(Article.builder()
                        .title("Viện KSND Tối cao nhận định về vụ tai nạn dẫn đến nổ súng ở Vĩnh Long")
                        .summary("Ngày 3.5, Viện KSND Tối cao cho biết, thực hiện ý kiến chỉ đạo của Viện trưởng Viện KSND tối cao, các đơn vị nghiệp vụ Viện KSND Tối cao đã kiểm tra lại quyết định giải quyết khiếu nại số 55/QĐ-VKS ngày 14.3.2025 đã có hiệu lực pháp luật của Viện KSND tỉnh Vĩnh Long.")
                        .thumbnailUrl("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                        .publishedAt(Instant.now().minus(30, ChronoUnit.SECONDS))
                        .build())
                .build());

        for(ArticleWithCategory a : articles){
            items.add(new ArticleViewItem(a, ArticleViewItem.TypeDisplay.NOTIFICATION));
        }
        ArticleRecycleViewAdapter adapter = new ArticleRecycleViewAdapter(items);
        recyclerView = findViewById(R.id.list_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.back_icon).setOnClickListener(v -> {
            finish();
        });
    }
}