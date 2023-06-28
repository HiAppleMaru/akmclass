package kr.ac.kopo;

public class Calc {
	private int state = 0; // 0 초기, 1 좌항, 2 연산자, 3 우항, 4 결과
	private int display = 0;
	private int left;
	private String operator;
	
	
	public void onButton(String button) {
		//사용자가 누른 버튼을 받아서 뭘 할지 결정하면되는데... 여기서는 누른 버튼을 받아주는것만
		try {
			int digit = Integer.parseInt(button);
			
			onDigit(digit);
		} catch (NumberFormatException e) {
			onOperator(button);
			
		}
	}
	private void onOperator(String button) {
		if ("C".equals(button) ) {
			state = 0;
			display = 0;
		} else {
			if(state == 1 && isOperator(button) || state == 4 && isOperator(button) || state == 2 && isOperator(button)) {
				state = 2;
				
				left = display;
				operator = button;
				
			} else if(state == 3 && "calc".equals(button)) {
				state = 4;
				calc();
			}
			
		}
	
	}
	private void calc() {
		if(operator.equals("pls"))
			display += left;
		else if(operator.equals("min"))
			display = left - display;
		else if(operator.equals("mul"))
			display *= left;
		else if(operator.equals("div"))
			display = left / display;
	}
	private boolean isOperator(String button) {
		if(button.equals("pls"))
			return true;
		else if(button.equals("min"))
			return true;
		else if(button.equals("mul"))
			return true;
		else if(button.equals("div"))
			return true;
		
		return false;
	}
	private void onDigit(int digit) {
		if(state == 0) {
			state = 1;
			display = digit;
		} else if(state == 1 || state == 3) {
			display = (display * 10) + digit;
		} else if(state == 2) {
			state = 3;
			display = digit;
		} 
		
	
	}
//자동으로 생성도 됨. 많이 편함
	public int getDisplay() {

		return display;
	}
	
	
	
	
}
