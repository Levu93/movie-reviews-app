
(ns movie-reviews-app.models.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [korma.core :refer :all]
            [korma.db :refer :all])
  (:import java.sql.DriverManager))

(defdb db (mysql {:db "moviereview"
                     :user "admin"
                     :password "admin"}))
(defentity user
  (table :user))

(defentity movie
  (table :movie))

(defentity comment
  (table :comment))

(defentity rating
  (table :rating))

(defn add-user [params]
  (insert user
  (values params)))

(defn get-user [username password]
  (select user
  (where {:username username :password password})))

(defn get-users [role]
  (select user
  (where {:role role})))

(defn delete-user [username]
  (delete user
  (where {:username username})))

(defn add-movie [params]
  (insert movie
  (values params)))

(defn update-movie [params]
  (update movie
          (set-fields params)
          (where {:id (:id params)})))

(defn delete-movie [id]
  (delete movie
  (where {:id id})))

(defn get-movies []
  (select movie
  (order :released :DESC)))

(defn search-movie [text]
 (select movie
  (where (like :name text))
         (order :released :DESC)))

(defn findbyid-movie [id]
  (select movie
  (where {:id id})))

(defn add-comment [params]
  (insert comment
  (values params)))

(defn update-comment [params]
  (update comment
          (set-fields params)
          (where {:id (:id params)})))

(defn delete-comment [id]
  (delete comment
  (where {:id id})))

(defn find-comment-by-movieid [movieid]
  (select comment
  (where {:movie movieid})
          (order :id :ASC)))

(defn find-comment-by-userid [userid]
  (select comment
  (where {:user userid})
          (order :id :ASC)))

(defn add-rating [params]
  (insert rating
  (values params)))

(defn delete-rating [params]
  (delete rating
  (where {:movie (:movie params) :user (:user params)})))

(defn update-rating [params]
  (update rating
          (set-fields params)
          (where {:movie (:movie params) :user (:user params)})))

(defn find-rating-by-user-movie [movie user]
  (select rating
  (where {:user user :movie movie})))

(defn get-ratings [movie]
  (select rating
  (aggregate (avg :rating) :average)
  (where {:movie movie})
  (group :movie)))


