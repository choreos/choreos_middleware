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

ruby_block "remove-service61c46b5b-86f8-45bb-bff0-3332182ee2e8" do
  block do
  	node.run_list.remove("recipe[service61c46b5b-86f8-45bb-bff0-3332182ee2e8::jar]")
  end
  only_if { node.run_list.include?("recipe[service61c46b5b-86f8-45bb-bff0-3332182ee2e8::jar]") }
end

ruby_block "remove-deactivate-service61c46b5b-86f8-45bb-bff0-3332182ee2e8" do
  block do
    node.run_list.remove("recipe[deactivate-service61c46b5b-86f8-45bb-bff0-3332182ee2e8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service61c46b5b-86f8-45bb-bff0-3332182ee2e8]") }
  notifies :stop, "service[service_61c46b5b-86f8-45bb-bff0-3332182ee2e8_jar]", :immediately
end
