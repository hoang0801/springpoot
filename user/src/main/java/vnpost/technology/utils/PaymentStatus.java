package vnpost.technology.utils;

public enum PaymentStatus {
    PENDING,     // Chưa thanh toán
    COMPLETED,   // Đã thanh toán thành công
    FAILED,      // Thanh toán thất bại
    REFUNDED     // Đã hoàn tiền
}
