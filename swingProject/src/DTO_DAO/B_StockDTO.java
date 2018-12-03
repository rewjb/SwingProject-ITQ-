package DTO_DAO;

public class B_StockDTO {

		private String id;//가맹점 아이디
		private	String name;//재고 품명
		private	String quantity;//재고 수량
		
		
		public B_StockDTO(String id, String name, String quantity) {
			super();
			this.id = id;
			this.name = name;
			this.quantity = quantity;
		}
		
		public B_StockDTO() {//기본생성자
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getQuantity() {
			return quantity;
		}

		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		
		
}//클래스 끝
