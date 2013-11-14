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

ruby_block "remove-activate-servicec9779d6f-893e-41e5-874e-fc73a8009dba" do
  block do
    node.run_list.remove("recipe[activate-servicec9779d6f-893e-41e5-874e-fc73a8009dba]")
  end
  only_if { node.run_list.include?("recipe[activate-servicec9779d6f-893e-41e5-874e-fc73a8009dba]") }
end


ruby_block "remove-deactivate-servicec9779d6f-893e-41e5-874e-fc73a8009dba" do
  block do
    node.run_list.remove("recipe[deactivate-servicec9779d6f-893e-41e5-874e-fc73a8009dba]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec9779d6f-893e-41e5-874e-fc73a8009dba]") }
  notifies :stop, "service[service_c9779d6f-893e-41e5-874e-fc73a8009dba_jar]", :immediately
end
