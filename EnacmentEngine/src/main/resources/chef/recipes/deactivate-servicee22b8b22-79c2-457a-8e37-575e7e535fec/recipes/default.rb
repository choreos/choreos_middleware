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

ruby_block "remove-servicee22b8b22-79c2-457a-8e37-575e7e535fec" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee22b8b22-79c2-457a-8e37-575e7e535fec::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e22b8b22-79c2-457a-8e37-575e7e535fec.war]", :immediately
end

ruby_block "remove-servicee22b8b22-79c2-457a-8e37-575e7e535fec" do
  block do
  	node.run_list.remove("recipe[servicee22b8b22-79c2-457a-8e37-575e7e535fec::war]")
  end
  only_if { node.run_list.include?("recipe[servicee22b8b22-79c2-457a-8e37-575e7e535fec::war]") }
end

ruby_block "remove-deactivate-servicee22b8b22-79c2-457a-8e37-575e7e535fec" do
  block do
    node.run_list.remove("recipe[deactivate-servicee22b8b22-79c2-457a-8e37-575e7e535fec]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee22b8b22-79c2-457a-8e37-575e7e535fec]") }
end
