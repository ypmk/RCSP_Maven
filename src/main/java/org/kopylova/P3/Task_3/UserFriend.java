package org.kopylova.P3.Task_3;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserFriend {
    private final int userId;
    private final int friendId;

    public UserFriend(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public int getUserId() {
        return userId;
    }

    public int getFriendId() {
        return friendId;
    }

    @Override
    public String toString() {
        return "UserFriend{userId=" + userId + ", friendId=" + friendId + "}";
    }

    public static List<UserFriend> generateRandomUserFriends(int size) {
        List<UserFriend> userFriends = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int userId = random.nextInt(100);
            int friendId = random.nextInt(100);
            userFriends.add(new UserFriend(userId, friendId));
        }
        return userFriends;
    }

    public static Observable<UserFriend> getFriends(List<UserFriend> userFriends, int userId) {
        return Observable.fromIterable(userFriends)
                .filter(userFriend -> userFriend.getUserId() == userId);
    }

    public static void main(String[] args) {
        System.out.println("Kopylova");

        List<UserFriend> userFriendsList = generateRandomUserFriends(1000);

        // Массив случайных userId
        Integer[] userIds = {1, 20, 35, 50}; // Пример случайных userId для поиска

        // Преобразуем массив userIds в поток
        Observable.fromArray(userIds)
                // Для каждого userId получаем его друзей через функцию getFriends
                .flatMap(userId -> getFriends(userFriendsList, userId))
                // Подписываемся на поток и выводим результат
                .subscribe(
                        userFriend -> System.out.println("Найден друг: " + userFriend),
                        Throwable::printStackTrace,
                        () -> System.out.println("Поиск друзей завершен.")
                );
    }
}