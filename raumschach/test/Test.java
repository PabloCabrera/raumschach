package raumschach.test;
class Test {

	protected void testMsg (String msg) {
		System.out.println ("TEST:   " + msg);
	}

	protected void success (String msg) {
		System.out.println ("SUCCESS:" + msg);
	}

	protected void success () {
		System.out.println ("SUCCESS ");
	}

	protected void fail () {
		System.out.println ("FAILED ");
	}

	protected void fail (String msg) {
		System.out.println ("FAILED: " + msg);
	}

	protected void errorMsg (String msg) {
		System.out.println ("ERROR:  " + msg);
	}

}
