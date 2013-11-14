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

ruby_block "disable-service899f6b91-3823-4068-9d1c-8a2301744885" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service899f6b91-3823-4068-9d1c-8a2301744885::jar]") }
  notifies :stop, "service[service_899f6b91-3823-4068-9d1c-8a2301744885_jar]", :immediately
end

ruby_block "remove-service899f6b91-3823-4068-9d1c-8a2301744885" do
  block do
  	node.run_list.remove("recipe[service899f6b91-3823-4068-9d1c-8a2301744885::jar]")
  end
  only_if { node.run_list.include?("recipe[service899f6b91-3823-4068-9d1c-8a2301744885::jar]") }
end

ruby_block "remove-deactivate-service899f6b91-3823-4068-9d1c-8a2301744885" do
  block do
    node.run_list.remove("recipe[deactivate-service899f6b91-3823-4068-9d1c-8a2301744885]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service899f6b91-3823-4068-9d1c-8a2301744885]") }
end
