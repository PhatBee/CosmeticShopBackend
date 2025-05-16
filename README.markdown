# Backend Cosmetic Shop

**Backend Cosmetic Shop** là hệ thống backend hỗ trợ ứng dụng Android **Cosmetic Shop**, một nền tảng thương mại điện tử chuyên về mỹ phẩm. Backend cung cấp các API RESTful để quản lý tài khoản người dùng, danh mục sản phẩm, giỏ hàng, đơn hàng, thanh toán, đánh giá sản phẩm, và địa chỉ giao hàng. Dự án được phát triển trong khuôn khổ môn **Lập trình di động** tại Trường Đại học Sư phạm Kỹ thuật TP.HCM (HCMUTE) bởi Nhóm 38.

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/PhatBee/CosmeticShopBackend)

## Mục Lục
- [Giới Thiệu Dự Án](#giới-thiệu-dự-án)
- [Tính Năng](#tính-năng)
- [Công Nghệ Sử Dụng](#công-nghệ-sử-dụng)
- [Cài Đặt](#cài-đặt)
- [Cấu Trúc Dự Án](#cấu-trúc-dự-án)
- [Cơ Sở Dữ Liệu](#cơ-sở-dữ-liệu)
- [Tài Liệu API](#tài-liệu-api)
- [Cách Sử Dụng](#cách-sử-dụng)
- [Đóng Góp](#đóng-góp)
- [Giấy Phép](#giấy-phép)
- [Liên Hệ](#liên-hệ)

## Giới Thiệu Dự Án
**Backend Cosmetic Shop** cung cấp nền tảng cho ứng dụng Cosmetic Shop trên Android, cho phép người dùng duyệt sản phẩm mỹ phẩm, thêm vào giỏ hàng, đặt hàng, quản lý địa chỉ giao hàng, và gửi đánh giá sản phẩm. Được xây dựng bằng Spring Boot và MySQL, backend cung cấp các API có khả năng mở rộng cao. Hệ thống tích hợp Cloudinary để lưu trữ hình ảnh và hỗ trợ thanh toán qua VNPay. Dự án thể hiện việc áp dụng các khái niệm lập trình di động vào một ứng dụng thương mại điện tử thực tế.

## Tính Năng
- **Quản lý người dùng**: Đăng ký, đăng nhập, đăng xuất, khôi phục mật khẩu, xác thực OTP, và cập nhật hồ sơ.
- **Quản lý sản phẩm**: Xem danh sách sản phẩm, tìm kiếm theo từ khóa hoặc danh mục, và xem chi tiết sản phẩm.
- **Quản lý giỏ hàng**: Thêm, cập nhật, xóa, và xóa toàn bộ sản phẩm trong giỏ hàng.
- **Quản lý đơn hàng**: Tạo, xem, hủy, và theo dõi đơn hàng với chi tiết đơn hàng và thanh toán.
- **Quản lý địa chỉ**: Thêm, cập nhật, xóa, và thiết lập địa chỉ giao hàng mặc định.
- **Đánh giá sản phẩm**: Gửi, cập nhật, và xem đánh giá và xếp hạng sản phẩm.
- **Danh sách yêu thích**: Thêm, xóa, và xem danh sách sản phẩm yêu thích.
- **Thanh toán**: Hỗ trợ thanh toán khi nhận hàng (COD) và cổng thanh toán VNPay.
- **Quản lý banner**: Lấy danh sách banner quảng cáo.

## Công Nghệ Sử Dụng
- **Ngôn ngữ lập trình**: Java 17
- **Framework**: Spring Boot 3.x
- **ORM**: Spring Data JPA (Hibernate)
- **Cơ sở dữ liệu**: MySQL 8.0
- **Công cụ xây dựng**: Maven
- **IDE**: IntelliJ IDEA
- **Kiểm tra API**: Postman
- **Thư viện**:
  - Spring Web (API RESTful)
  - Spring Data JPA (quản lý cơ sở dữ liệu)
  - Lombok (giảm mã boilerplate)
  - MySQL Connector (kết nối cơ sở dữ liệu)

## Cài Đặt

### Yêu Cầu
- **Java**: JDK 17 hoặc cao hơn
- **Maven**: 3.6.0 hoặc cao hơn
- **MySQL**: 8.0 hoặc cao hơn
- **IntelliJ IDEA**: Khuyến nghị cho phát triển
- **Postman**: Dùng để kiểm tra API
- **Git**: Để clone repository

### Các Bước Cài Đặt
1. **Clone Repository**
   ```bash
   git clone https://github.com/PhatBee/CosmeticShopBackend.git
   cd CosmeticShopBackend
   ```

2. **Cấu Hình Cơ Sở Dữ Liệu MySQL**
   - Tạo cơ sở dữ liệu MySQL:
     ```sql
     CREATE DATABASE cosmetic_shop;
     ```
   - Cập nhật file `application.properties` trong thư mục `src/main/resources`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/cosmetic_shop?useSSL=false&serverTimezone=UTC
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

3. **Cài Đặt Thư Viện**
   - Chạy lệnh sau để tải các thư viện:
     ```bash
     mvn clean install
     ```

4. **Chạy Ứng Dụng**
   - Khởi động ứng dụng Spring Boot:
     ```bash
     mvn spring-boot:run
     ```
   - Backend sẽ hoạt động tại `http://localhost:8080`.

5. **Kiểm Tra Cài Đặt**
   - Sử dụng Postman để gửi yêu cầu GET tới `http://localhost:8080/api/health` (nếu đã triển khai):
     ```json
     {"status": "UP"}
     ```

## Cấu Trúc Dự Án
```
CosmeticShopBackend/
├── src/
│   ├── main/
│   │   ├── java/com/phatbee/cosmeticshopbackend/
│   │   │   ├── config/          # Các lớp cấu hình (CloudinaryConfig)
│   │   │   ├── controller/      # Các controller REST cho API
│   │   │   ├── entity/          # Các entity JPA (User, Product, Cart)
│   │   │   ├── repository/      # Các repository Spring Data JPA
│   │   │   ├── service/         # Logic nghiệp vụ và các lớp service
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Các lớp xử lý ngoại lệ tùy chỉnh
│   │   │   └── CosmeticShopBackendApplication.java  # Lớp chính của ứng dụng
│   │   └── resources/
│   │       ├── application.properties  # File cấu hình
├── pom.xml                      # File quản lý thư viện Maven
├── README.md                    # Tài liệu dự án
└── docs/                        # Tài liệu API (Postman collection, sơ đồ cơ sở dữ liệu)
```

## Cơ Sở Dữ Liệu
Backend sử dụng cơ sở dữ liệu MySQL với các bảng chính sau:
- **User**: Lưu thông tin người dùng (`userId`, `email`, `password`, `fullname`, `phone`, `gender`).
- **Product**: Lưu chi tiết sản phẩm (`productId`, `productName`, `price`, `image`, `brand`).
- **Cart**: Quản lý giỏ hàng (`cartId`, `customerId`).
- **CartItem**: Liên kết sản phẩm với giỏ hàng (`cartItemId`, `cartId`, `productId`, `quantity`).
- **Order**: Lưu chi tiết đơn hàng (`orderId`, `customerId`, `total`, `orderStatus`).
- **OrderLine**: Chi tiết sản phẩm trong đơn hàng (`orderLineId`, `orderId`, `productId`).
- **Address**: Quản lý địa chỉ giao hàng (`addressId`, `customerId`, `address`).
- **ProductFeedback**: Lưu đánh giá sản phẩm (`productFeedbackId`, `productId`, `rating`, `comment`).
- **Wishlist**: Quản lý danh sách yêu thích (`wishlistId`, `productId`, `userId`).
- **Category**: Lưu danh mục sản phẩm (`categoryId`, `categoryName`, `image_url`).
- **Banner**: Lưu banner quảng cáo (`bannerId`, `image_url`).
- **Payment**: Lưu thông tin thanh toán (`paymentId`, `orderId`, `total`, `paymentMethod`).
- **ShippingAddress**: Lưu địa chỉ giao hàng của đơn hàng (`shippingAddressId`, `orderId`, `address`).

Xem chi tiết lược đồ tại [Sơ Đồ Cơ Sở Dữ Liệu](docs/database.png).

## Tài Liệu API
Tài liệu API được cung cấp qua Postman collection. Danh sách các endpoint chính được liệt kê dưới đây.

- **Postman Collection**: Tải [Cosmetic Shop Postman Collection](docs/postman_collection.json) từ thư mục `docs`.

### Các Endpoint Chính
#### Xác Thực
- `POST /api/auth/register`: Đăng ký người dùng mới.
- `POST /api/auth/login`: Đăng nhập.
- `POST /api/auth/verify-otp`: Xác thực OTP khi đăng ký.
- `POST /api/auth/resend-otp`: Gửi lại OTP đăng ký.
- `POST /api/auth/forgot-password`: Yêu cầu OTP khôi phục mật khẩu.
- `POST /api/auth/reset-password`: Đặt lại mật khẩu bằng OTP.
- `POST /api/auth/resend-password-reset-otp`: Gửi lại OTP khôi phục mật khẩu.

#### Quản Lý Người Dùng
- `GET /api/user/{userId}`: Lấy thông tin người dùng.
- `PUT /api/user/{userId}`: Cập nhật hồ sơ người dùng.

#### Quản Lý Sản Phẩm
- `GET /api/products`: Lấy danh sách tất cả sản phẩm.
- `GET /api/products/{productId}`: Lấy chi tiết sản phẩm.
- `GET /api/products/category/{categoryId}`: Lấy sản phẩm theo danh mục.
- `GET /api/products/search?keyword={keyword}`: Tìm kiếm sản phẩm.
- `GET /api/products/recent`: Lấy sản phẩm mới nhất.
- `GET /api/products/top-selling`: Lấy sản phẩm bán chạy.
- `GET /api/products/{productId}/average-rating`: Lấy điểm đánh giá trung bình.
- `GET /api/product-feedbacks/product/{productId}`: Lấy đánh giá của sản phẩm.

#### Danh Mục & Banner
- `GET /api/categories`: Lấy danh sách danh mục.
- `GET /api/banners`: Lấy danh sách banner quảng cáo.

#### Quản Lý Giỏ Hàng
- `GET /api/cart/user/{userId}`: Lấy giỏ hàng của người dùng.
- `POST /api/cart/add`: Thêm sản phẩm vào giỏ hàng.
- `PUT /api/cart/update`: Cập nhật số lượng sản phẩm trong giỏ.
- `DELETE /api/cart/remove/{userId}/{cartItemId}`: Xóa sản phẩm khỏi giỏ.
- `DELETE /api/cart/clear/{userId}`: Xóa toàn bộ giỏ hàng.

#### Quản Lý Đơn Hàng
- `POST /api/orders/create`: Tạo đơn hàng mới.
- `POST /api/order-lines/create`: Thêm chi tiết đơn hàng.
- `GET /api/orders/user/{userId}`: Lấy danh sách đơn hàng của người dùng.
- `POST /api/orders/cancel/{orderId}`: Hủy đơn hàng.
- `POST /api/orders/create-vnpay-url`: Tạo URL thanh toán VNPay.

#### Thanh Toán
- `POST /api/payments/create`: Tạo bản ghi thanh toán.

#### Quản Lý Địa Chỉ
- `GET /api/addresses/user/{userId}`: Lấy danh sách địa chỉ của người dùng.
- `GET /api/addresses/default/{userId}`: Lấy địa chỉ mặc định.
- `POST /api/addresses/add`: Thêm địa chỉ mới.
- `PUT /api/addresses/update`: Cập nhật địa chỉ.
- `DELETE /api/addresses/delete/{addressId}`: Xóa địa chỉ.
- `POST /api/shipping-addresses/create`: Tạo địa chỉ giao hàng cho đơn hàng.

#### Danh Sách Yêu Thích
- `POST /api/wishlist/add`: Thêm sản phẩm vào danh sách yêu thích.
- `DELETE /api/wishlist/remove`: Xóa sản phẩm khỏi danh sách yêu thích.
- `GET /api/wishlist/user/{userId}`: Lấy danh sách yêu thích.
- `GET /api/wishlist/check`: Kiểm tra sản phẩm có trong danh sách yêu thích.

#### Đánh Giá
- `POST /api/feedback`: Gửi đánh giá sản phẩm.
- `PUT /api/feedback/{feedbackId}`: Cập nhật đánh giá sản phẩm.
- `GET /api/feedback/order/{orderId}`: Lấy đánh giá theo đơn hàng.

## Cách Sử Dụng
### Ví Dụ: Đăng Ký Người Dùng
```bash
curl -X POST "http://localhost:8080/api/auth/register" \
-H "Content-Type: application/json" \
-d '{
  "email": "user@example.com",
  "password": "password123",
  "fullname": "Nguyen Van A",
  "phone": "0123456789",
  "gender": "MALE"
}'
```
Phản hồi:
```json
{
  "status": "success",
  "message": "Đăng ký thành công. Vui lòng xác thực OTP."
}
```

### Ví Dụ: Lấy Giỏ Hàng
```bash
curl -X GET "http://localhost:8080/api/cart/user/1" \
-H "Content-Type: application/json"
```
Phản hồi:
```json
{
  "cartId": 1,
  "customerId": 1,
  "cartItems": [
    {
      "cartItemId": 1,
      "product": {
        "productId": 1,
        "productName": "Son môi",
        "price": 150000,
        "image": "https://res.cloudinary.com/your_cloud_name/image/upload/v1234567890/lipstick.jpg"
      },
      "quantity": 2
    }
  ]
}
```

## Đóng Góp
Chúng tôi hoan nghênh mọi đóng góp! Để đóng góp:
1. Fork repository.
2. Tạo branch mới: `git checkout -b feature/your-feature`.
3. Thực hiện thay đổi và commit: `git commit -m "Thêm tính năng mới"`.
4. Push lên branch: `git push origin feature/your-feature`.
5. Tạo Pull Request trên GitHub.


## Liên Hệ
- **Tác giả**: Ong Vĩnh Phát, Huỳnh Thị Mỹ Tâm
- **GitHub**: [PhatBee](https://github.com/PhatBee)
- **Báo lỗi**: [GitHub Issues](https://github.com/PhatBee/CosmeticShopBackend/issues)
