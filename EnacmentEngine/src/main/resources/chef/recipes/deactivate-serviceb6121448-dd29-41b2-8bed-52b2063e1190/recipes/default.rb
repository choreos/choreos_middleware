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

ruby_block "remove-serviceb6121448-dd29-41b2-8bed-52b2063e1190" do
  block do
  	node.run_list.remove("recipe[serviceb6121448-dd29-41b2-8bed-52b2063e1190::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb6121448-dd29-41b2-8bed-52b2063e1190::jar]") }
end

ruby_block "remove-deactivate-serviceb6121448-dd29-41b2-8bed-52b2063e1190" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb6121448-dd29-41b2-8bed-52b2063e1190]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb6121448-dd29-41b2-8bed-52b2063e1190]") }
  notifies :stop, "service[service_b6121448-dd29-41b2-8bed-52b2063e1190_jar]", :immediately
end
