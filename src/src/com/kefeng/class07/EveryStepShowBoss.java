package com.kefeng.class07;

import java.util.*;

/**
 * 给定一个boolean数组，表示每个人在该时刻是否购买该商品，每次购买后需要选出购买商品最多的前两名作为幸运者，问每次购买后的前两名是？
 */
public class EveryStepShowBoss {

    public static class Customer {
        public int id;
        public int buy;
        public int enterTime;

        public Customer(int i, int b, int e) {
            id = i;
            buy = b;
            enterTime = e;
        }
    }

    public static class CandidateComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            /**候选区需要根据用户购买商品数降序排列。如果购买商品数相同，则需根据进入候选区的时间升序排列*/
            return o1.buy != o2.buy ? o2.buy - o1.buy : o1.enterTime - o2.enterTime;
        }
    }

    public static class DaddyComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            /**得奖区需要根据用户的购买数升序排列，如果用户的购买数相同，则需根据进入得奖区的时间升序排列*/
            return o1.buy != o2.buy ? o1.buy - o2.buy : o1.enterTime - o2.enterTime;
        }
    }

    public static class WhosYourDaddy {
        private HashMap<Integer, Customer> customers;
        private HeapGreater<Customer> candHeap;
        private HeapGreater<Customer> daddyHeap;
        private final int daddyLimit;

        public WhosYourDaddy(int limit) {
            customers = new HashMap<>();
            candHeap = new HeapGreater<>(new CandidateComparator());
            daddyHeap = new HeapGreater<>(new DaddyComparator());
            daddyLimit = limit;
        }

        public void operate(int time, int id, boolean buyOrRefund) {
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }
            Customer c = customers.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                customers.remove(id);
            }
            if (!candHeap.contains(c) && !daddyHeap.contains(c)) {
                if (daddyHeap.size() < daddyLimit) {
                    c.enterTime = time;
                    daddyHeap.push(c);
                } else {
                    c.enterTime = time;
                    candHeap.push(c);
                }
            } else if (candHeap.contains(c)) {
                if (c.buy == 0) {
                    candHeap.remove(c);
                } else {
                    candHeap.resign(c);
                }
            } else {
                if (c.buy == 0) {
                    daddyHeap.remove(c);
                } else {
                    daddyHeap.resign(c);
                }
            }
            daddyMove(time);
        }

        public List<Integer> getDaddies() {
            List<Customer> allElements = daddyHeap.getAllElements();
            List<Integer> ans = new ArrayList<>();
            for (Customer c : allElements
            ) {
                ans.add(c.id);
            }
            return ans;
        }

        private void daddyMove(int time) {
            if (candHeap.isEmpty()) {
                return;
            }
            if (daddyHeap.size() < daddyLimit) {
                Customer c = candHeap.pop();
                c.enterTime = time;
                daddyHeap.push(c);
            } else {
                if (candHeap.peek().buy > daddyHeap.peek().buy) {
                    Customer newDaddy = candHeap.pop();
                    Customer oldDaddy = daddyHeap.pop();
                    newDaddy.enterTime = time;
                    oldDaddy.enterTime = time;
                    daddyHeap.push(newDaddy);
                    candHeap.push(oldDaddy);
                }
            }
        }

    }

    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhosYourDaddy whoDaddies = new WhosYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whoDaddies.operate(i, arr[i], op[i]);
            ans.add(whoDaddies.getDaddies());
        }
        return ans;
    }

    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        /**用户购买商品后，在map中会记录*/
        Map<Integer, Customer> map = new HashMap<>();
        /**候选区的用户*/
        ArrayList<Customer> cands = new ArrayList<>();
        /**获奖区的用户*/
        ArrayList<Customer> daddy = new ArrayList<>();
        /**每个用户购买后得到的获奖用户*/
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            /**如果用户没有购买或者购买数为0，并且用户退货，此时需要把上一个用户购买后得到的获奖者复制过来*/
            if (!buyOrRefund && !map.containsKey(id)) {
                ans.add(getCurAns(daddy));
                continue;
            }
            /**来到这里表示没有用户购买数是0且要退货。此时用户购买数为0，则会购买商品；用户购买数>0，则会购买商品或者退货*/
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            /**如果用户购买数不是0，则执行买或者卖*/
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            /**如果用户的购买数是0，则在map,cands,daddy中都需要删掉该用户*/
            if (c.buy == 0) {
                map.remove(id);
            }
            /**如果在候选区和得奖区都不存在该用户，则表示该用户首次购买商品，或者之前进出过候选区或者得奖区*/
            if (!cands.contains(c) && !daddy.contains(c)) {
                if (daddy.size() < k) {
                    c.enterTime = i;
                    daddy.add(c);
                } else {
                    c.enterTime = i;
                    cands.add(c);
                }
            }
            /**清理候选区和得奖区中购买数为0的用户*/
            cleanZeroBuy(cands);
            cleanZeroBuy(daddy);
            cands.sort(new CandidateComparator());
            daddy.sort(new DaddyComparator());
            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
        /**如果候选区为空，直接返回*/
        if (cands.isEmpty()) {
            return;
        }
        /**如果候选区不为空，且得奖区没有满，从候选区拿出用户放到得奖区即可*/
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            c.enterTime = time;
            daddy.add(c);
            cands.remove(0);
        } else {
            /**如果候选区不为空，得奖区满，则需要执行比较然后替换*/
            if (cands.get(0).buy > daddy.get(0).buy) {
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                oldDaddy.enterTime = time;
                newDaddy.enterTime = time;
                daddy.add(newDaddy);
                cands.add(oldDaddy);
            }
        }
    }

    public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer c : daddy
        ) {
            ans.add(c.id);
        }
        return ans;
    }


    public static void cleanZeroBuy(ArrayList<Customer> arr) {
        List<Customer> noZero = new ArrayList<>();
        for (Customer c : arr
        ) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero
        ) {
            arr.add(c);
        }
    }

    // 为了测试
    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort((a, b) -> a - b);
            cur2.sort((a, b) -> a - b);
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " , " + op[j]);
                }
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
