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

ruby_block "remove-activate-servicef5a01cc2-dbd5-49fa-95b8-dffeaf66f014" do
  block do
    node.run_list.remove("recipe[activate-servicef5a01cc2-dbd5-49fa-95b8-dffeaf66f014]")
  end
  only_if { node.run_list.include?("recipe[activate-servicef5a01cc2-dbd5-49fa-95b8-dffeaf66f014]") }
end


ruby_block "remove-deactivate-servicef5a01cc2-dbd5-49fa-95b8-dffeaf66f014" do
  block do
    node.run_list.remove("recipe[deactivate-servicef5a01cc2-dbd5-49fa-95b8-dffeaf66f014]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef5a01cc2-dbd5-49fa-95b8-dffeaf66f014]") }
  notifies :stop, "service[service_f5a01cc2-dbd5-49fa-95b8-dffeaf66f014_jar]", :immediately
end
