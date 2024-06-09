package com.mira.mira.view.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mira.mira.data.model.Notification

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> get() = _notifications

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        val notificationList = listOf(
            Notification(1, "Reservasi Anda Telah Dikonfirmasi!", "Lihat detailnya di halaman history reservasi dan pastikan semua sudah siap.", "Hari ini 17:00", false),
            Notification(2, "Hasil Pemeriksaan Anda Telah Tersedia", "Periksa hasil lengkapnya di halaman result dan unduh laporan Anda sekarang.", "Kemarin 17:00", false),
            Notification(3, "Berita Kesehatan Terbaru Hari Ini", "Temukan informasi kesehatan terbaru dan menarik di artikel hari ini.", "Hari ini 18:00", false)
        )
        _notifications.value = notificationList
    }

    fun updateNotification(updatedNotification: Notification) {
        _notifications.value = _notifications.value?.map { notification ->
            if (notification.id == updatedNotification.id) updatedNotification else notification
        }
    }

    fun markAllAsRead() {
        _notifications.value = _notifications.value?.map { it.copy(isRead = true) }
    }
}
