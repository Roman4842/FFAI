package com.example.ffai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String VERSION_NAME = "2.0";

    private TextView statusText;
    private Button startButton;
    private Button stopButton;
    private Button sendButton;
    private EditText messageInput;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI
        statusText = findViewById(R.id.statusText);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        sendButton = findViewById(R.id.sendButton);
        messageInput = findViewById(R.id.messageInput);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        // Чат
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        // Кнопки
        startButton.setOnClickListener(v -> statusText.setText("FFAI " + VERSION_NAME + " працює"));
        stopButton.setOnClickListener(v -> statusText.setText("FFAI " + VERSION_NAME + " зупинено"));
        
sendButton.setOnClickListener(v -> {
            String text = messageInput.getText().toString().trim();
            if (!text.isEmpty()) {
                // Повідомлення користувача
                chatMessages.add(new ChatMessage(text, true));
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                
                // Відповідь AI
                chatMessages.add(new ChatMessage("FFAI " + VERSION_NAME + ": " + text, false));
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                
                chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                messageInput.setText("");
            }
        });

        statusText.setText("FFAI " + VERSION_NAME);
        
        // Привітання
        chatMessages.add(new ChatMessage("🚀 FFAI " + VERSION_NAME + " запущено!", false));
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
    }
}

class ChatMessage {
    private String message;
    private boolean isUser;

    public ChatMessage(String message, boolean isUser) {
        this.message = message;
        this.isUser = isUser;
    }

    public String getMessage() { return message; }
    public boolean isUser() { return isUser; }
}

class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<ChatMessage> messages;

    public ChatAdapter(ArrayList<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isUser() ? 0 : 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = viewType == 0 ? R.layout.item_user_message : R.layout.item_ai_message;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.messageText.setText(messages.get(position).getMessage());
    }

    @Override
    public int getItemCount() { return messages.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}