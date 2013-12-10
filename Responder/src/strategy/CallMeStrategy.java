package strategy;

import utilities.BoyerMoore;

import ai.Claire;

public class CallMeStrategy extends ConcretePhraseStrategy{

	public CallMeStrategy(){
		this.requiresSubject = true;
	}
	
	public String respondToPhrase(String text, Claire c) {
		String newName = "";
		
		String noCaps = text.toLowerCase();
		BoyerMoore bm = new BoyerMoore("call me ");
		int result = bm.search(noCaps);
		
		if(result==text.length())return failedRename();
		
		int start = result + 8;
		int end = text.length();
		for(int i=start;i<text.length();i++){
			if(text.charAt(i)==' '){
				end = i;
				break;
			}
		}
		newName = text.substring(start,end);
		if(newName.equals("")){
			return failedRename();
		}
		c.getMemory().setUsersName(newName);
		return "Okay... I'll call you " + newName;
	}

	private String failedRename() {
		return "Sorry? ... I didn't quite catch that.";
	}

	@Override
	public String respondToPhrase(Claire c) {
		// unsupported
		return null;
	}	
}
