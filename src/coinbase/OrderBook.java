package coinbase;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * @author priyamvora
 * @created 12/05/2021
 */
public class OrderBook {
    private final PriorityQueue<Integer> buyerQueue;
    private final PriorityQueue<Integer> sellerQueue;

    private final PriorityQueue<OrderDetails> buyerQueueWithExpiry;
    private final PriorityQueue<OrderDetails> sellerQueueWithExpiry;

    public OrderBook() {
        this.buyerQueue = new PriorityQueue<>(Collections.reverseOrder());
        this.sellerQueue = new PriorityQueue<>();
        this.buyerQueueWithExpiry = new PriorityQueue<>((order1, order2) -> order1.getAmount() - order2.getAmount());
        this.sellerQueueWithExpiry = new PriorityQueue<>((order1, order2) -> order2.getAmount() - order1.getAmount());
    }

    public void addBuyerToQueue(Integer buyerAmount) {
        buyerQueue.add(buyerAmount);
    }

    public void addBuyerToQueue(Integer buyerAmount, Long expiry) {
        buyerQueueWithExpiry.add(new OrderDetails(buyerAmount, expiry));
    }

    public void addSellerToQueue(Integer sellAmount, Long expiry) {
        sellerQueueWithExpiry.add(new OrderDetails(sellAmount, expiry));
    }

    public void addSellerToQueue(Integer sellerAmount) {
        sellerQueue.add(sellerAmount);
    }

    public Integer matchSellerForBuyer(Integer buyAmount) {
        if (!sellerQueue.isEmpty()) {
            Integer lowestSellerPrice = sellerQueue.peek();
            if (lowestSellerPrice <= buyAmount) {
                return sellerQueue.poll();
            }
        }
        return -1;
    }

    public Integer matchBuyerForSeller(Integer sellAmount) {
        if (!buyerQueue.isEmpty()) {
            Integer highestBuyPrice = buyerQueue.peek();
            if (highestBuyPrice >= sellAmount) {
                return buyerQueue.poll();
            }
        }
        return -1;
    }

    public Integer matchSellerForBuyerWithExpiry(Integer buyAmount) {
        while (!sellerQueueWithExpiry.isEmpty() && sellerQueueWithExpiry.peek().getExpiry() < System.currentTimeMillis() / 1000) {
            sellerQueueWithExpiry.poll();
        }

        if (!sellerQueue.isEmpty()) {
            Integer lowestSellerPrice = sellerQueue.peek();
            if (lowestSellerPrice <= buyAmount) {
                return sellerQueue.poll();
            }
        }
        return -1;
    }

    public Integer matchBuyerForSellerWithExpiry(Integer sellAmount) {
        if (!buyerQueue.isEmpty()) {
            Integer highestBuyPrice = buyerQueue.peek();
            if (highestBuyPrice >= sellAmount) {
                return buyerQueue.poll();
            }
        }
        return -1;
    }
}

class OrderDetails {
    private final Integer amount;
    private final Long expiry;

    public OrderDetails(Integer amount, Long expiry) {
        this.amount = amount;
        this.expiry = expiry;
    }

    public Integer getAmount() {
        return amount;
    }

    public Long getExpiry() {
        return expiry;
    }
}
