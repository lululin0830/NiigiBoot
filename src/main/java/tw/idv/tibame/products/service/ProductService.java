package tw.idv.tibame.products.service;

import java.util.List;

import tw.idv.tibame.products.entity.Product;

public interface ProductService {
	
	public List<Product> getAllFindLatestProducts() throws Exception;
	
	public List<Product> getAllExpensiveProducts() throws Exception;
	
	public List<Product> getKeywordProducts(String keyword) throws Exception;
	
	public List<Product> getCategorieProducts(String categorie) throws Exception;
	
	
	
	// 新增商品(欄位不得為空值)

	// 修改商品(欄位不得為空值)

	// 將html表單獲取到的搜尋字串為轉為String[] keywords
	// // 假設從 HTML 表單獲取到的搜尋字串為 searchQuery
	// String searchQuery = "手機 冰箱 水果";
	//
	// // 使用空格將 searchQuery 字串分開成多個關鍵字
	// String[] keywords = searchQuery.split("\\s+");
	//
	// // keywords 陣列中將包含三個元素：{"手機", "冰箱", "水果"}
	
	// 查詢後小分類
//		public Map<String, Integer> getDistinctCategories(List<Product> productList) {
//		    Map<String, Integer> categoryCountMap = new HashMap<>();
//		    for (Product product : productList) {
//		        String categoryName = product.getCategorieName();
//		        // 如果分類名稱已經在Map中存在，則將計數器加1；否則將分類名稱加入Map並設置計數器為1
//		        categoryCountMap.put(categoryName, categoryCountMap.getOrDefault(categoryName, 0) + 1);
//		    }
//		    return categoryCountMap;
//		    
//		    Iterator<Map.Entry<String, Integer>> entryIterator = categoryCountMap.entrySet().iterator();
//		    int count = 0;
//		    
//		    while (entryIterator.hasNext()) {
//		        Map.Entry<String, Integer> entry = entryIterator.next();
//		        if (count < 3 ) {
//			        String key = entry.getKey();
//			        Integer value = entry.getValue();	        	
//		        }else {
//		        	
//		        }
//		        
//
//		        // 使用 key 和 value 或進行其他操作
//		        //...
//		    }
//		    
//		    
//		}
	
	// 查詢排序
//		public List<Product> sortCategorieKeyword(List<Product> resultList, String sortKeyword) {
//		    resultList.sort(Comparator.comparing(product -> {
//		        switch (sortKeyword) {
//		            case "firstOnShelvesDate":
//		                return product.getFirstOnShelvesDate();
//		            case "avgRating":
//		                return product.getAvgRating();
//		            case "productPrice":
//		                return product.getProductPrice();
//		            // 其他需要排序的欄位...
//		        }
//		    }).reversed());
//		    return resultList;
//		}

}
