import java.util.ArrayList;
import java.util.List;

class FactoryMethodDemo {

    public abstract class Room {
        abstract void connect(Room room);
    }

    public class MagicRoom extends Room {
        public void connect(Room room) {
        }
    }

    public class OrdinaryRoom extends Room {
        public void connect(Room room) {
        }
    }

    public abstract class MazeGame {
        private final List<Room> rooms = new ArrayList<>();

        public MazeGame() {
            Room room1 = makeRoom("magic");
            Room room2 = makeRoom("ordinary");
            room1.connect(room2);
            rooms.add(room1);
            rooms.add(room2);
        }

        abstract protected Room makeRoom(String kind);
    }

    public class ConcreteMazeGame extends MazeGame {
        @Override
        protected Room makeRoom(String kind) {
            switch (kind) {
                case "magic":
                    return new MagicRoom();
                case "ordinary":
                    return new OrdinaryRoom();
            }
            throw new UnsupportedOperationException();
        }
    }

    MazeGame game = new ConcreteMazeGame();
}