(ns movie-reviews-app.routes.movie
  (:require [compojure.core :refer :all]
            [selmer.parser :refer [render-file]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [response redirect content-type]]
            [movie-reviews-app.models.db :as db]))

(defn authenticated [session]
  (authenticated? session))

(defn israted [id user]
  (some? (first (db/find-rating-by-user-movie id user))))

(defn getrating [id user]
  (db/find-rating-by-user-movie id user))

(defn averagerating [movie]
  (format "%.1f" (:average (first (db/get-ratings movie)))))

(defn check-authenticated-admin [session]
  (and (authenticated? session)
       (="admin" (:role (:identity session)))))

(defn showmovie [{:keys [params session] request :request}]
  (println (:identity session))
  (render-file "pages/moviedetail.html"
               {:movie (first (db/findbyid-movie (:id params)))
                :user (:identity session)
                :average (averagerating (:id params))
                :rating (first (db/find-rating-by-user-movie (:id params) (:username (:identity session))))
                :rated (israted (:id params) (:username (:identity session)))
                :comments (db/find-comment-by-movieid (:id params))
                :authenticated (str (authenticated session))}))

(defn addmovie-get [{:keys [params session] request :request}]
  (if (check-authenticated-admin session)
      (render-file "pages/addmovie.html" {:user (:identity session)})
      (render-file "pages/login.html" {:error "Please log in to continue this action!!!"})))

(defn addmovie-post [{:keys [params session] request :request}]
  (db/add-movie params)
  (render-file "pages/home.html" {:movies (db/get-movies) :user (:identity session)}))

(defn addcomment [{:keys [params session] request :request}]
  (db/add-comment params)
  (redirect (str "/moviedetail/" (:movie params))))

(defn editcomment [{:keys [params session] request :request}]
  (db/update-comment params)
  (redirect (str "/moviedetail/" (:movie params))))

(defn deletecomment [{:keys [params session] request :request}]
  (db/delete-comment (:id params))
  (redirect (str "/moviedetail/" (:movie params))))

(defroutes movie-routes
  (GET "/moviedetail/:id" request (showmovie request))
  (GET "/addmovie" request (addmovie-get request))
  (POST "/addmovie" request (addmovie-post request))
  (POST "/addcomment" request (addcomment request))
  (POST "/editcomment" request (editcomment request))
  (GET "/deletecomment/:movie&:id" request (deletecomment request)))
