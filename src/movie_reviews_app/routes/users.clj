(ns movie-reviews-app.routes.users
  (:require [compojure.core :refer :all]
            [selmer.parser :refer [render-file]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [response redirect content-type]]
            [movie-reviews-app.models.db :as db]))

(defn authenticated [session]
  (authenticated? session))

(defn check-authenticated-admin [session]
  (and (authenticated? session)
       (="admin" (:role (:identity session)))))

(defn users [{:keys [params session] request :request}]
  (if (check-authenticated-admin session)
    (render-file "pages/users.html" {:users (db/get-users "user") :authenticated (str (authenticated session)) :user (:identity session)})
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn deleteuser [{:keys [params session] request :request}]
  (if (check-authenticated-admin session)
  (do (db/delete-user (:username params))
    (redirect "/users"))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defroutes users-routes
  (GET "/users" request (users request))
  (GET "/deleteuser/:username" request (deleteuser request)))
