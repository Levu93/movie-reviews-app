(ns movie-reviews-app.routes.auth
  (:require [compojure.core :refer :all]
            [selmer.parser :refer [render-file]]
            [movie-reviews-app.models.db :as db]
            [ring.util.response :refer [redirect]]))

(defn login-get []
  (render-file "pages/login.html" {}))

(defn login-post [{:keys [params session] request :request}]
  (let [user (first (db/get-user (:username params) (:password params)))]
    (if (some? user)
      (assoc (redirect "/"):session (assoc session :identity user))
      (render-file "pages/login.html" {:error "Wrong username or password"}))))

(defn logout
  [request]
  (-> (redirect "/")
      (assoc :session {})))

(defn register-get []
  (render-file "pages/register.html" {}))

(defn register-post [{:keys [params session] request :request}]
  (db/add-user params)
  (let [user (first (db/get-user (:username params) (:password params)))]
    (assoc (redirect "/"):session (assoc session :identity user))))

(defroutes auth-routes
  (GET "/login" [] (login-get))
  (POST "/login" request (login-post request))
  (GET "/logout" request (logout request))
  (GET "/register" [] (register-get))
  (POST "/register" request (register-post request)))
