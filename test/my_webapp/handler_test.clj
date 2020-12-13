(ns my-webapp.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [my-webapp.views :as views]
            [my-webapp.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      #_(is (= (:page response) views/home-page (mock/request :get "/")))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
