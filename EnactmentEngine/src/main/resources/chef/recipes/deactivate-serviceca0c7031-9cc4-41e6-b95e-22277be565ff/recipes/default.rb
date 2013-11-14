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

ruby_block "remove-serviceca0c7031-9cc4-41e6-b95e-22277be565ff" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceca0c7031-9cc4-41e6-b95e-22277be565ff::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ca0c7031-9cc4-41e6-b95e-22277be565ff.war]", :immediately
end

ruby_block "remove-serviceca0c7031-9cc4-41e6-b95e-22277be565ff" do
  block do
  	node.run_list.remove("recipe[serviceca0c7031-9cc4-41e6-b95e-22277be565ff::war]")
  end
  only_if { node.run_list.include?("recipe[serviceca0c7031-9cc4-41e6-b95e-22277be565ff::war]") }
end

ruby_block "remove-deactivate-serviceca0c7031-9cc4-41e6-b95e-22277be565ff" do
  block do
    node.run_list.remove("recipe[deactivate-serviceca0c7031-9cc4-41e6-b95e-22277be565ff]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceca0c7031-9cc4-41e6-b95e-22277be565ff]") }
end
