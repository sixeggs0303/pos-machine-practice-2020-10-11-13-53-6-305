package pos.machine;

import java.util.List;

public class PosMachine {

    public String printReceipt(List<String> barcodes) {
        int total = 0;
        StringBuilder receipt = new StringBuilder("***<store earning no money>Receipt***\n");
        List<ItemInfo> allItemInfo = ItemDataLoader.loadAllItemInfos();

        String[] distinctBarcodes = barcodes.stream().distinct().toArray(String[]::new);
        for (String barcode : distinctBarcodes) {
            ItemInfo itemInfo = findItemByBarcode(allItemInfo, barcode);
            int amount = countItem(barcodes, barcode);
            receipt.append(createLine(itemInfo, amount));       // concat the receipt
            total += itemInfo.getPrice() * amount;              // accumulate the total
        }

        receipt.append("----------------------\nTotal: ")
                .append(total)
                .append(" (yuan)\n**********************");

        return receipt.toString();
    }

    private int countItem(List<String> barcodes, String targetBarcode) {
        return barcodes.stream().filter(o -> o.equals(targetBarcode)).toArray().length;
    }

    private ItemInfo findItemByBarcode(List<ItemInfo> allItemInfo, String barcode) {
        return allItemInfo.stream().filter(item -> item.getBarcode().equals(barcode)).findFirst().orElse(null);
    }

    private String createLine(ItemInfo itemInfo, int amount) {
        return "Name: " + itemInfo.getName() + ", Quantity: " + amount + ", Unit price: " + itemInfo.getPrice() + " (yuan), Subtotal: " + amount * itemInfo.getPrice() + " (yuan)\n";
    }
}