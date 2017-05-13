(ns movie-reviews-app.routes.auth
  (:require [compojure.core :refer :all]
            [selmer.parser :refer [render-file]]
            [ring.util.response :refer [redirect]]
            [movie-reviews-app.views.layout :as layout]))

(defn login-get []
  (render-file "pages/login.html" {}))

(defn login-post [{:keys [params session] request :request}]
  (assoc (redirect "/"):session (assoc session :identity "")))

(defn logout
  [request]
  (-> (redirect "/login")
      (assoc :session {})))

(defn register-get []
  (render-file "pages/register.html" {}))

(defn register-post [{:keys [params session] request :request}]
  (assoc (redirect "/"):session (assoc session :identity "")))

(defroutes auth-routes
  (GET "/login" [] (login-get))
  (POST "/login" request (login-post request))
  (GET "/logout" request (logout request))
  (GET "/register" [] (register-get))
  (POST "/register" request (register-post request)))
