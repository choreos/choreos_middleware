#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-servicea227a71c-5656-46af-8717-23ba5fd56722" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea227a71c-5656-46af-8717-23ba5fd56722::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/a227a71c-5656-46af-8717-23ba5fd56722.war]", :immediately
end

ruby_block "remove-servicea227a71c-5656-46af-8717-23ba5fd56722" do
  block do
  	node.run_list.remove("recipe[servicea227a71c-5656-46af-8717-23ba5fd56722::war]")
  end
  only_if { node.run_list.include?("recipe[servicea227a71c-5656-46af-8717-23ba5fd56722::war]") }
end

ruby_block "remove-deactivate-servicea227a71c-5656-46af-8717-23ba5fd56722" do
  block do
    node.run_list.remove("recipe[deactivate-servicea227a71c-5656-46af-8717-23ba5fd56722]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea227a71c-5656-46af-8717-23ba5fd56722]") }
end
