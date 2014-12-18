package raumschach.event;

public class PromotionEvent extends MoveEvent {
	protected byte promotionType;

	public PromotionEvent (
		int eventType, long time, boolean color,
		byte pieceType, int[] origin,
		int[] destination, byte promotionType
	) throws Exception {
		super (eventType, time, color, pieceType, origin, destination);
		this.promotionType = promotionType;
	}

	public byte getPromotionType () {
		return this.promotionType;
	}
}
