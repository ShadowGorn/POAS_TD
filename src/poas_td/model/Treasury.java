package poas_td.model;

public class Treasury {

	private int _gold_count = 0;

	public void add_gold(short delta) {
            _gold_count += delta;
	}

	public void decrease_gold(short delta) {
            _gold_count -= delta;
	}

	public void set_gold(int count) {
            _gold_count = count;
	}
        
        public int get_gold() {
            return _gold_count;
        }
}
