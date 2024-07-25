package ra.run;

import ra.entity.Laptop;
import ra.entity.LaptopType;
import util.InputMethods;

import java.util.Date;

public class Main {
    private static LaptopType[] laptopTypes = new LaptopType[10]; // Giả sử sử dụng mảng có kích thước cố định cho laptops
    private static Laptop[] laptops = new Laptop[10];
    private static int nextLaptopTypeIndex = 0;
    private static int nextLaptopIndex = 0;


    public static void main(String[] args) {
        while (true) {
            System.out.println("******************LAPTOP-MANAGEMENT******************\n" +
                    "1. Quản lý loại laptop\n" +
                    "2. Quản lý laptop\n" +
                    "3. Thoát");
            System.out.println("Nhập vào lựa chọn của bạn:");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    laptopTypeMenu();
                    break;
                case 2:
                    laptopMenu();
                    break;
                case 3:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
            if (choice == 3) {
                break;
            }

        }
    }

    private static void laptopTypeMenu() {
        while (true) {
            System.out.println("*******************LAPTOP_TYPE-MENU********************\n" +
                    "1. Hiển thị danh sách các loại laptop\n" +
                    "2. Thêm mới loại laptop\n" +
                    "3. Cập nhật thông tin loại laptop\n" +
                    "4. Xóa loại Laptop\n" +
                    "5. Thoát\n");
            System.out.println("Nhập vào lựa chọn của bạn:");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    displayLaptopTypes();
                    break;
                case 2:
                    addLaptopType();
                    break;
                case 3:
                    updateLaptopType();
                    break;
                case 4:
                    deleteLaptopType();
                    break;
                case 5:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
            if (choice == 5) {
                break;
            }
        }


    }

    private static void laptopMenu() {
        while (true) {
            System.out.println("********************LAPTOP-MENU*********************\n" +
                    "1. Danh sách Laptop\n" +
                    "2. Thêm mới Laptop\n" +
                    "3. Cập nhật thông tin Laptop\n" +
                    "4. Xóa Laptop\n" +
                    "5. Thống kê số lượng laptop theo từng loại\n" +
                    "6. Thoát");
            System.out.println("Nhập vào lựa chọn của bạn:");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    displayLaptops();
                    break;
                case 2:
                    addLaptop();
                    break;
                case 3:
                    updateLaptop();
                    break;
                case 4:
                    deleteLaptop();
                    break;
                case 5:
                    countLaptopsByType();
                    break;
                case 6:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
            if (choice == 6) {
                break;
            }
        }
    }

    private static void displayLaptopTypes() {
        boolean checkIsDelete = true;
        if (nextLaptopTypeIndex == 0) {
            System.out.println("Hiện tại danh sách loại laptop đang trống,hãy thêm loại lap top!");
            addLaptopType();
        } else {
            System.out.println("Danh sách các loại laptop:");
            for (int i = 0; i < nextLaptopTypeIndex; i++) {
                LaptopType laptopType = laptopTypes[i];
                if (!laptopType.isDeleted()) {
                    System.out.printf("|Id: %s| Kiểu Laptop: %-10s| Mô tả: %-10s\n", laptopType.getTypeId(), laptopType.getTypeName(), laptopType.getDescription());
                    checkIsDelete = false;
                }
            }
            if (checkIsDelete) {
                System.out.println("Hiện tại danh sách loại laptop đang trống,hãy thêm loại lap top!");
                addLaptopType();
            }
        }

    }

    private static void addLaptopType() {
        // Kiểm tra và mở rộng mảng nếu cần thiết
        if (nextLaptopTypeIndex >= laptopTypes.length) {
            LaptopType[] newLaptopTypes = new LaptopType[laptopTypes.length * 2];
            System.arraycopy(laptopTypes, 0, newLaptopTypes, 0, laptopTypes.length);
            laptopTypes = newLaptopTypes;
        }

        System.out.println("Thêm mới loại laptop:");
        System.out.print("Nhập tên loại laptop: ");
        String typeName = InputMethods.getString().trim();

        // Kiểm tra xem typeName đã tồn tại trong danh sách các loại laptop
        boolean typeNameExists = false;
        for (int i = 0; i < nextLaptopTypeIndex; i++) {
            LaptopType laptopType = laptopTypes[i];
            if (laptopType.getTypeName().equalsIgnoreCase(typeName)) {
                typeNameExists = true;
                if (laptopType.isDeleted()) {
                    System.err.println("Loại laptop này đã bị xóa. Bạn có muốn khôi phục loại laptop này không?");
                    System.err.println("1. Khôi phục loại laptop này");
                    System.err.println("2. Nhập lại tên loại laptop khác");
                    byte choice = InputMethods.getByte();
                    switch (choice) {
                        case 1:
                            laptopType.setDeleted(false);
                            System.out.println("Loại laptop đã được khôi phục.");
                            return;
                        case 2:
                            addLaptopType();
                            return;
                        default:
                            System.err.println("Lựa chọn không hợp lệ. Quay lại menu trước đó.");
                            return;
                    }
                } else {
                    System.err.println("Tên loại laptop đã tồn tại trong danh sách.Vui lòng nhập lại tên loại laptop khác!");
                    addLaptopType();
                    return;
                }
            }
        }

        // Nếu typeName chưa tồn tại trong danh sách, tiến hành thêm mới
        if (!typeNameExists) {
            System.out.print("Nhập mô tả: ");
            String description = InputMethods.getString().trim();

            // Tạo một loại laptop mới
            int typeId = nextLaptopTypeIndex + 1;
            boolean isDeleted = false;

            LaptopType newLaptopType = new LaptopType(typeId, typeName, description, isDeleted);
            laptopTypes[nextLaptopTypeIndex] = newLaptopType;
            nextLaptopTypeIndex++;

            System.out.println("Loại laptop đã được thêm mới thành công.");
        }
    }


    private static void updateLaptopType() {
        System.out.println("Cập nhật thông tin loại laptop:");
        System.out.print("Nhập ID loại laptop cần cập nhật: ");
        int typeIdToUpdate = InputMethods.getInteger();

        LaptopType laptopTypeToUpdate = findLaptopTypeById(typeIdToUpdate);
        if (laptopTypeToUpdate == null) {
            System.err.println("Không tìm thấy loại laptop có ID = " + typeIdToUpdate);
            return;
        }

        System.out.print("Nhập tên mới cho loại laptop: ");
        String newTypeName = InputMethods.getString();

        System.out.print("Nhập mô tả mới: ");
        String newDescription = InputMethods.getString();

        laptopTypeToUpdate.setTypeName(newTypeName);
        laptopTypeToUpdate.setDescription(newDescription);

        System.out.println("Thông tin loại laptop đã được cập nhật thành công.");
    }

    private static void deleteLaptopType() {
        System.out.println("Xóa loại laptop:");
        System.out.print("Nhập ID loại laptop cần xóa: ");
        int typeIdToDelete = InputMethods.getInteger();

        LaptopType laptopTypeToDelete = findLaptopTypeById(typeIdToDelete);
        if (laptopTypeToDelete == null) {
            System.err.println("Không tìm thấy loại laptop có ID = " + typeIdToDelete);
            return;
        }

        laptopTypeToDelete.setDeleted(true);

        System.out.println("Loại laptop đã được đánh dấu xóa thành công.");
    }

    private static LaptopType findLaptopTypeById(int typeId) {
        for (int i = 0; i < nextLaptopTypeIndex; i++) {
            if (laptopTypes[i].getTypeId() == typeId && !laptopTypes[i].isDeleted()) {
                return laptopTypes[i];
            }
        }
        return null;
    }

    private static String findLaptopNameTypeById(int typeId) {
        for (int i = 0; i < nextLaptopTypeIndex; i++) {
            if (laptopTypes[i].getTypeId() == typeId && !laptopTypes[i].isDeleted()) {
                return laptopTypes[i].getTypeName();
            }
        }
        return null;
    }
    //LapTops Method

    private static void displayLaptops() {
        boolean checkIsDelete = true;
        if (nextLaptopIndex == 0) {
            System.out.println("Hiện tại không có laptop nào trong danh sách!");
            return;
        } else {
            System.out.println("Danh sách các laptop:");
            for (int i = 0; i < nextLaptopIndex; i++) {
                Laptop laptop = laptops[i];
                if (!laptop.isDeleted()) {
                    System.out.printf("|Id: %s| Tên Laptop: %-5s| Mô tả: %-5s| RAM: %-1d| Trọng lượng: %-5.2f| Ngày tạo: %-10s| Giá: %-10.2f| Loại Laptop:%-10s\n",
                            laptop.getLaptopId(), laptop.getLaptopName(), laptop.getDescription(), laptop.getRam(), laptop.getWeight(),
                            laptop.getCreateAt(), laptop.getLaptopPrice(),findLaptopNameTypeById(laptop.getTypeId()));
                    checkIsDelete = false;
                }
            }
        }
        if (checkIsDelete) {
            System.out.println("Hiện tại không có laptop nào trong danh sách!");
        }
    }


    private static void addLaptop() {
        // Kiểm tra và mở rộng mảng nếu cần thiết
        if (nextLaptopIndex >= laptops.length) {
            Laptop[] newLaptops = new Laptop[laptops.length * 2];
            System.arraycopy(laptops, 0, newLaptops, 0, laptops.length);
            laptops = newLaptops;
        }

        System.out.println("Thêm mới laptop:");

        // Tạo laptopId tự động theo định dạng L****
        String laptopId = generateLaptopId();

        // Nhập tên laptop
        System.out.print("Nhập tên laptop: ");
        String laptopName = InputMethods.getString().trim();

        // Kiểm tra tên laptop không để trống
        if (laptopName.isEmpty()) {
            System.err.println("Tên laptop không được để trống!");
            return;
        }

        // Nhập mô tả
        System.out.print("Nhập mô tả: ");
        String description = InputMethods.getString().trim();

        // Kiểm tra mô tả không để trống
        if (description.isEmpty()) {
            System.err.println("Mô tả không được để trống!");
            return;
        }

        // Nhập RAM
        int ram;
        while (true) {
            System.out.print("Nhập RAM (GB): ");
            ram = InputMethods.getInteger();
            if (ram > 0) {
                break;
            } else {
                System.err.println("RAM phải lớn hơn 0!");
            }
        }

        // Nhập trọng lượng
        double weight;
        while (true) {
            System.out.print("Nhập trọng lượng (kg): ");
            weight = InputMethods.getDouble();
            if (weight > 0) {
                break;
            } else {
                System.err.println("Trọng lượng phải lớn hơn 0!");
            }
        }

        // Nhập giá laptop
        double laptopPrice;
        while (true) {
            System.out.print("Nhập giá laptop: ");
            laptopPrice = InputMethods.getDouble();
            if (laptopPrice > 0) {
                break;
            } else {
                System.err.println("Giá laptop phải lớn hơn 0!");
            }
        }

        // Nhập typeId (chọn từ danh sách loại laptop)
        if (nextLaptopTypeIndex == 0) {
            System.err.println("Hiện tại chưa có danh sách loại laptop,vui lòng thêm loại laptop trước khi thêm laptop!");
            return;
        }else {
            displayLaptopTypes();
        }

        int typeId;
        while (true) {
            System.out.print("Nhập mã loại laptop: ");
            typeId = InputMethods.getInteger();
            boolean typeIdExists = false;
            for (int i = 0; i < nextLaptopTypeIndex; i++) {
                if (laptopTypes[i].getTypeId() == typeId && !laptopTypes[i].isDeleted()) {
                    typeIdExists = true;
                    break;
                }
            }
            if (typeIdExists) {
                break;
            } else {
                System.err.println("Mã loại laptop không hợp lệ!");
            }
        }

        boolean isDeleted = false;

        // Tạo đối tượng laptop mới
        Laptop newLaptop = new Laptop(
                laptopId,
                laptopName,
                description,
                ram,
                weight,
                new Date(),
                laptopPrice,
                typeId,
                isDeleted
        );

        laptops[nextLaptopIndex] = newLaptop;
        nextLaptopIndex++;

        System.out.println("Laptop đã được thêm mới thành công.");
    }

    private static void updateLaptop() {
        System.out.println("Cập nhật thông tin laptop:");
        System.out.print("Nhập ID laptop cần cập nhật: ");
        String laptopIdToUpdate = InputMethods.getString().trim();

        Laptop laptopToUpdate = findLaptopById(laptopIdToUpdate);
        if (laptopToUpdate == null) {
            System.err.println("Không tìm thấy laptop có ID = " + laptopIdToUpdate);
            return;
        }

        // Nhập RAM mới
        int newRam;
        while (true) {
            System.out.print("Nhập RAM mới (GB): ");
            newRam = InputMethods.getInteger();
            if (newRam > 0) {
                laptopToUpdate.setRam(newRam);
                break;
            } else {
                System.err.println("RAM phải lớn hơn 0!");
            }
        }

        // Nhập trọng lượng mới
        double newWeight;
        while (true) {
            System.out.print("Nhập trọng lượng mới (kg): ");
            newWeight = InputMethods.getDouble();
            if (newWeight > 0) {
                laptopToUpdate.setWeight(newWeight);
                break;
            } else {
                System.err.println("Trọng lượng phải lớn hơn 0!");
            }
        }

        // Nhập giá mới
        double newLaptopPrice;
        while (true) {
            System.out.print("Nhập giá mới: ");
            newLaptopPrice = InputMethods.getDouble();
            if (newLaptopPrice > 0) {
                laptopToUpdate.setLaptopPrice(newLaptopPrice);
                break;
            } else {
                System.err.println("Giá laptop phải lớn hơn 0!");
            }
        }

        // Nhập typeId mới
        displayLaptopTypes();
        int newTypeId;
        while (true) {
            System.out.print("Nhập mã loại laptop mới: ");
            newTypeId = InputMethods.getInteger();
            boolean typeIdExists = false;
            for (int i = 0; i < nextLaptopTypeIndex; i++) {
                if (laptopTypes[i].getTypeId() == newTypeId && !laptopTypes[i].isDeleted()) {
                    typeIdExists = true;
                    break;
                }
            }
            if (typeIdExists) {
                laptopToUpdate.setTypeId(newTypeId);
                break;
            } else {
                System.err.println("Mã loại laptop không hợp lệ!");
            }
        }

        System.out.println("Thông tin laptop đã được cập nhật thành công.");
    }

    private static void deleteLaptop() {
        System.out.println("Xóa laptop:");
        System.out.print("Nhập ID laptop cần xóa: ");
        String laptopIdToDelete = InputMethods.getString().trim();

        Laptop laptopToDelete = findLaptopById(laptopIdToDelete);
        if (laptopToDelete == null) {
            System.err.println("Không tìm thấy laptop có ID = " + laptopIdToDelete);
            return;
        }

        laptopToDelete.setDeleted(true);

        System.out.println("Laptop đã được đánh dấu xóa thành công.");
    }

    private static void countLaptopsByType() {
        if (nextLaptopIndex == 0) {
            System.out.println("Hiện tại không có laptop nào trong danh sách!");
            return;
        }

        // Tạo một mảng để lưu số lượng laptop của từng loại
        int[] laptopCounts = new int[nextLaptopTypeIndex];

        // Đếm số lượng laptop cho từng loại
        for (int i = 0; i < nextLaptopIndex; i++) {
            Laptop laptop = laptops[i];
            if (!laptop.isDeleted()) {
                int typeId = laptop.getTypeId() - 1;
                laptopCounts[typeId]++;
            }
        }

        // In ra kết quả thống kê
        System.out.println("Thống kê số lượng laptop theo từng loại:");
        for (int i = 0; i < nextLaptopTypeIndex; i++) {
            LaptopType laptopType = laptopTypes[i];
            if (!laptopType.isDeleted()) {
                System.out.printf("|Loại Laptop: %-10s| Số lượng: %-10d\n", laptopType.getTypeName(), laptopCounts[i]);
            }
        }
    }


    private static Laptop findLaptopById(String laptopId) {
        for (int i = 0; i < nextLaptopIndex; i++) {
            if (laptops[i].getLaptopId().equals(laptopId) && !laptops[i].isDeleted()) {
                return laptops[i];
            }
        }
        return null; // Không tìm thấy laptop có laptopId tương ứng
    }


    private static String generateLaptopId() {
        // Tìm số lượng laptop đã thêm vào để sinh tiếp theo
        int count = nextLaptopIndex + 1;
        return String.format("L%04d", count);
    }

}
