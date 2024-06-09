package com.mira.mira.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.mira.mira.data.model.Message
import com.mira.mira.databinding.ItemMessageBinding

class MessageAdapter(options: FirebaseRecyclerOptions<Message>) :
    FirebaseRecyclerAdapter<Message, MessageAdapter.MessageViewHolder>(options) {

    class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.tvMessenger.text = message.senderName // Use senderName
            binding.tvMessage.text = message.text
            // You might want to format timestamp to a readable date
            binding.tvTimestamp.text = java.text.DateFormat.getDateTimeInstance().format(message.timestamp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMessageBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        holder.bind(model)
    }
}
