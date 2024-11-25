package vnpost.technology.utils;

public enum OrderStatus {
    PENDING, // Chờ xử lý
    PICKED_UP, // Đã lấy hàng
    IN_TRANSIT, // Đang giao
    DELIVERED, // Đã giao hàng
    CANCELED; // Đã hủy
}
