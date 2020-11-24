package pos.machine;

import java.util.List;

public class PosMachine {

    public String printReceipt(List<String> barcodes) {
        int total = 0;
        String receipt = "***<store earning no money>Receipt***\n";
        String[] distinctBarcodes = barcodes.stream().distinct().toArray(String[]::new);

        for(String barcode: distinctBarcodes){
            ItemInfo itemInfo = findItemByBarcode(barcode);
            int amount = countItem(barcodes, barcode);
            receipt += createLine(itemInfo, amount);
            total += itemInfo.getPrice() * amount;
        }

        receipt += "----------------------\n" + "Total: " + total + " (yuan)\n" + "**********************";

        return receipt;
    }

    private int countItem(List<String> barcodes, String targetBarcode) {
        return barcodes.stream().filter(o -> o.equals(targetBarcode)).toArray().length;
    }

    private ItemInfo findItemByBarcode(String barcode){
        return ItemDataLoader.loadAllItemInfos().stream().filter(item -> item.getBarcode().equals(barcode)).findFirst().orElse(null);
    }

    private String createLine(ItemInfo itemInfo, int amount){
        return "Name: " + itemInfo.getName() + ", Quantity: " + amount + ", Unit price: " + itemInfo.getPrice() + " (yuan), Subtotal: " + amount*itemInfo.getPrice() + " (yuan)\n";
    }
}
