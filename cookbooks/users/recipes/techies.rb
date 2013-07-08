#
# Cookbook Name:: users
# Recipe:: default
#
# Copyright 2011, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#
user "cucumber-chef" do
  supports :manage_home => true
  home "/home/cucumber-chef"
  shell "/bin/bash"
end

directory "/home/cucumber-chef/.ssh" do
  owner cucumber-chef
  group cucumber-chef
  mode "0700"
end

cookbook_file "/home/cucumber-chef/.ssh/authorized_keys" do
  source "authorized_keys"
  owner cucumber-chef
  group cucumber-chef
  mode "0600"
end