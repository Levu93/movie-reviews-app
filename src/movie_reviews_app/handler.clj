(ns movie-reviews-app.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [movie-reviews-app.routes.auth :refer [auth-routes]]
            [movie-reviews-app.routes.home :refer [home-routes]]
            [movie-reviews-app.routes.users :refer [users-routes]]
            [movie-reviews-app.routes.movie :refer [movie-routes]]))

(def backend (session-backend))

(defn init []
  (selmer.parser/cache-off!)
  (println "movie-reviews-app is starting"))

(defn destroy []
  (println "movie-reviews-app is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes auth-routes users-routes movie-routes home-routes app-routes)
      (handler/site)
      (wrap-authorization backend)
      (wrap-authentication backend)))
