// NAME: RAY LUU

import java.util.StringTokenizer;
import data_structures.*;

public class ExpressionEvaluator {
	private Stack <String> stack;
	private Queue <String> queue;
	private StringTokenizer Token;
	private int openParenCounter = 0;
	private int closeParenCounter = 0;
	
	public ExpressionEvaluator(){
		stack = new Stack <String>();
		queue = new Queue <String>();
	}
					
	public String getPostFix(String infix){
		stack.makeEmpty();
		queue.makeEmpty();
		Token = new StringTokenizer(infix);
		int length = Token.countTokens();
		
		try{	
			/*
			 * if ( the token is "(" )
			 * push the "(" onto the stack
			 */
			for(int i=1; i <= length; i++){
				String word = Token.nextToken();
				if(word.equals("(")){
					openParenCounter++;
					stack.push(word);			
				}
				
				/*
				 * if ( the token is ")" )
				 * while the last element in the stack is "(" and stack size is greater than 1 or
				 the first and the last element in the stack is not "("
				 *  pop an operator off the stack and enqueue it in the queue
				 */
				if(word.equals( ")" )){
					closeParenCounter++;
					while(stack.peekLast().contains("(") && stack.size() > 1 ||
							!stack.peekLast().contains("(") && !stack.peek().contains("(") ){
						String s = stack.pop();
							queue.enqueue(s);
					}	
				} 
				
				/*
				 * if the token is an operator
				 *  while(the stack is not empty && the top of the stack is not a "(" 
			  	&& the precedence of the token on the top of the stack >= current token)
			  	 *  push current token onto the stack
				 */
				if(word.equals("+") || word.equals("-") || word.equals("*") || word.equals("/") || word.equals("^") ){
					while ((!stack.isEmpty()) && (stack.peek() != "(") && (isGreaterOrEqual(stack.peek(),word))){
						String st = stack.pop();
						queue.enqueue(st);						
					}
					stack.push(word);
				}
				
				/*
				 * if ( token is a number )
				 * enqueue token
				 */
				if(Character.isDigit(word.charAt(0))){  // enqueue if it is number
					queue.enqueue(word); 
				}
			} 
			
			/*
			 * while ( stack not empty )
			 * pop token off stack and enqueue it
			 */
			while(!stack.isEmpty()){			
				String str = stack.pop();
				queue.enqueue(str);				
			}	
		}
		
		catch(Exception e){
			return "ERROR";
		}
		
		/*
		 * return queue because The queue now has the postfix expression
		 */
		return queue+"";	
	}
	
	public String evaluatePostFix(String w){
		try{
			/*
			 * if the the number of open parenthesis is not equal to number of close parenthesis
			 * reset number of close and open parenthesis to 0 for the next input
			 * then return error
			 */
			if(openParenCounter != closeParenCounter ){
				openParenCounter = 0;
				closeParenCounter = 0;
				return "ERROR";
			}
			
			while(!queue.isEmpty()){
				
				/*
				 *  if ( token is a number )
				 *  push onto the stack
				 */
				for(int i= 1; i <= queue.size(); i++){
					if(Character.isDigit(queue.peek().charAt(0))){
						String dequeue = queue.dequeue() ;
						stack.push(dequeue);
					}		
				}
				
				/*
				 * if the first and last element in the queue is "(" 
				 * return fist element in  the queue
				 */
				if(queue.peek().contains("(") && queue.peekLast().contains("(")) 
					queue.remove(queue.peek()); 
				
				/*
				 * else if the first element in the queue is "(" and the last element in the queue is not "("
				 * return the first element in the queue
				 */
				else if(queue.peek().contains("(") && (!queue.peekLast().contains("(")))
					queue.remove(queue.peek());
				
				/*
				 * if the first element in the queue contain an operator
				 * pop the top two tokens off the stack
				 * perform token_from_stack2 operator token_from_stack1
				 * remove the operator from the queue
				 * push results onto the stack
				 * return Error if something else other than operators
				 */
				if(queue.peek().contains("+") || queue.peek().contains("-") || queue.peek().contains("*") || 
					queue.peek().contains("/") || queue.peek().contains("^")){	 
						double number1 = Double.parseDouble(stack.pop());
						double number2 = Double.parseDouble(stack.pop());

						if(queue.peek().contains("+")){
							queue.remove(queue.peek());
							double sum = number2 + number1;
							stack.push(sum+"");
						}
						else if(queue.peek().contains("-")){
							queue.remove(queue.peek());
							double sub = number2 - number1;
							stack.push(sub+"");
						}
						else if(queue.peek().contains("*")){
							queue.remove(queue.peek());
							double multi = number2 * number1;		
							stack.push(multi+"");
						}
						else if(queue.peek().contains("/")){
							queue.remove(queue.peek());
							double div = number2 / number1;
							if(div == Double.POSITIVE_INFINITY || div == Double.NEGATIVE_INFINITY){
								return "Division by Zero";
							}
							stack.push(div+"");
						}
						else if(queue.peek().contains("^")){
							queue.remove(queue.peek());
							double expo = Math.pow(number2, number1);
							if(expo == Double.POSITIVE_INFINITY || expo == Double.NEGATIVE_INFINITY){
								return "Infinity";
							}
							stack.push(expo+"");
						}
						else
							return "ERROR";
				}
				else
					throw new RuntimeException();	
			}
		}
		catch(Exception e){}
		
		/*
		 * if there is more than 1 element in the stack and there is no more operator in the queue
		 * return Error
		 */
		if(stack.size() > 1 && (!queue.peek().contains("+") || !queue.peek().contains("-") || !queue.peek().contains("*") || 
				!queue.peek().contains("/") || !queue.peek().contains("^"))){
			return "ERROR";
		}
		 String answer = stack.pop();
		 return answer;
	}
	
	public String processInput(String s){
		try{
			String newp = getPostFix(s);
			if(s == " ")
				return " ";
			String answer = evaluatePostFix(newp);
			return answer;	
		}
		catch(Exception e){
			return "ERROR";
		}
	}
	
	/*
	 * Comparison of precedence
	 * return the true if a has a greater precedence than b
	 * return false if it is not
	 */
	private static boolean isGreaterOrEqual(String a, String b){
		if(a.equals("^"))
			return true;
		if(b.equals("^"))
			return false;
		if(a.equals("*") || a.equals("/"))
			return true;
		if(b.equals("*") || b.equals("/"))
			return false;
		if(a.equals("+") || a.equals("-"))
			return true;
		if(b.equals("+") || b.equals("-"))
			return false;
		return true;
	}			
}