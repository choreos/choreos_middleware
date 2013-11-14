#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

ruby_block "remove-service3e0bc580-b5e4-44f2-affa-88e73444522b" do
  block do
  	node.run_list.remove("recipe[service3e0bc580-b5e4-44f2-affa-88e73444522b::jar]")
  end
  only_if { node.run_list.include?("recipe[service3e0bc580-b5e4-44f2-affa-88e73444522b::jar]") }
end

ruby_block "remove-deactivate-service3e0bc580-b5e4-44f2-affa-88e73444522b" do
  block do
    node.run_list.remove("recipe[deactivate-service3e0bc580-b5e4-44f2-affa-88e73444522b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service3e0bc580-b5e4-44f2-affa-88e73444522b]") }
  notifies :stop, "service[service_3e0bc580-b5e4-44f2-affa-88e73444522b_jar]", :immediately
end
